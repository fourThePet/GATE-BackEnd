package com.ureca.gate.place.infrastructure.jpaadapter;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.infrastructure.dto.PlaceResponse;
import com.ureca.gate.place.infrastructure.dto.QPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ureca.gate.place.infrastructure.jpaadapter.entity.QCategoryEntity.categoryEntity;
import static com.ureca.gate.place.infrastructure.jpaadapter.entity.QPlaceEntity.placeEntity;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PlaceResponse> findByQueryDsl(Point userLocation, String category, Size size, List<String> entryConditions, List<String> types) {
        return queryFactory
                .select(new QPlaceResponse(
                        placeEntity.id,
                        placeEntity.name,
                        placeEntity.categoryEntity.name,
                        placeEntity.address.roadAddress,
                        placeEntity.address.postalCode)
                )
                .from(placeEntity)
                .leftJoin(placeEntity.categoryEntity,categoryEntity)
                .where(
                        isWithinBuffer(userLocation, 1000),// 반경 조건
                        matchesCategory(category),
                        matchesSize(size), //사이즈
                        matchesEntryConditionsAnd(entryConditions), //제한사항
                        matchesTypes(types) // 타입 조건 추가
                )
                .fetch();
    }

    // 반경 조건 (ST_Buffer + ST_Contains 활용)
    private BooleanExpression isWithinBuffer(Point userLocation, double radiusMeters) {
        return Expressions.booleanTemplate(
                "ST_Contains(ST_Buffer(ST_GeomFromText({0}, 4326), {1}), {2})",
                "POINT(" + userLocation.getY() + " " + userLocation.getX() + ")", // 사용자 위치
                radiusMeters,                                                    // 반경
                placeEntity.address.locationPoint                                                   // 장소 위치
        );
    }
    // 카테고리 조건
    private BooleanExpression matchesCategory(String category) {
        if (category == null) {
            return null; // 조건이 없으면 필터링하지 않음
        }

        return JPAExpressions
                .select(categoryEntity.name) // 부모 이름 또는 자신의 이름 반환
                .from(categoryEntity)
                .where(
                        categoryEntity.eq(placeEntity.categoryEntity) // 현재 카테고리 매칭
                                .and(categoryEntity.parentCategory.isNull()) // 부모 없음
                                .or(categoryEntity.id.eq(placeEntity.categoryEntity.parentCategory.id)) // 부모 있음
                )
                .eq(category);
    }

    // 사이즈 조건
    private BooleanExpression matchesSize(Size size) {
        return size != null ? placeEntity.size.eq(size.name()) : null;
    }
    //입장조건
    private BooleanExpression matchesEntryConditionsAnd(List<String> entryConditions) {
        if (entryConditions == null || entryConditions.isEmpty()) {
            return null; // 조건이 없으면 필터링하지 않음
        }

        return entryConditions.stream()
                .map(this::mapConditionToExpression) // 각 조건을 BooleanExpression으로 변환
                .reduce(BooleanExpression::and)     // AND로 연결
                .orElse(null);                      // 조건이 없으면 null 반환
    }

    // 조건 이름을 BooleanExpression으로 매핑
    private BooleanExpression mapConditionToExpression(String condition) {
        switch (condition) {
            case "isLeashRequired":
                return placeEntity.isLeashRequired.eq("Y"); // "Y"로 필터링
            case "isMuzzleRequired":
                return placeEntity.isMuzzleRequired.eq("Y");
            case "isCageRequired":
                return placeEntity.isCageRequired.eq("Y");
            case "isVaccinationComplete":
                return placeEntity.isVaccinationComplete.eq("Y");
            default:
                throw new IllegalArgumentException("Unknown entry condition: " + condition);
        }
    }
    private BooleanExpression matchesTypes(List<String> types) {
        if (types == null || types.isEmpty()) {
            return null; // 조건이 없으면 필터링하지 않음
        }

        return types.stream()
                .map(this::mapTypeToExpression) // 각 타입을 BooleanExpression으로 변환
                .filter(Objects::nonNull)      // 유효한 조건만 포함
                .reduce(BooleanExpression::and) // AND로 연결
                .orElse(null);                  // 조건이 없으면 null 반환
    }

    // 각 타입을 BooleanExpression으로 매핑
    private BooleanExpression mapTypeToExpression(String type) {
        switch (type) {
            case "parkingAvailable":
                return placeEntity.parkingAvailable.eq("Y");
            case "indoorAvailable":
                return placeEntity.indoorAvailable.eq("Y");
            case "outdoorAvailable":
                return placeEntity.outdoorAvailable.eq("Y");
            default:
                throw new IllegalArgumentException("Unknown type condition: " + type);
        }
    }


}
