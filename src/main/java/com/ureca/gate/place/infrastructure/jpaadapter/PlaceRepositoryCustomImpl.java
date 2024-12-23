package com.ureca.gate.place.infrastructure.jpaadapter;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.gate.dog.domain.enumeration.Size;
import com.ureca.gate.place.infrastructure.command.PlaceCommand;
import com.ureca.gate.place.infrastructure.command.QPlaceCommand;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ureca.gate.dog.domain.enumeration.DogSizeGroup.*;
import static com.ureca.gate.place.infrastructure.jpaadapter.entity.QCategoryEntity.categoryEntity;
import static com.ureca.gate.place.infrastructure.jpaadapter.entity.QPlaceEntity.placeEntity;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //검색어 없을때, 반경기준
    @Override
    public List<PlaceCommand> findByQueryDsl(Point userLocation, Double latitude, Double longitude,String category, Size size, List<String> entryConditions, List<String> types) {

        System.out.println(userLocation.getY());
        System.out.println(userLocation.getX());
        System.out.println(longitude);
        System.out.println((latitude));
        return queryFactory
                .select(new QPlaceCommand(
                        placeEntity.id,
                        placeEntity.name,
                        placeEntity.categoryEntity.name,
                        placeEntity.photoUrl,
                        placeEntity.addressEntity.locationPoint,
                        placeEntity.addressEntity.roadAddress,
                        placeEntity.addressEntity.postalCode,
                        Expressions.numberTemplate(
                                        Double.class,
                                "function('ST_Distance', {0}, function('ST_SetSRID', function('ST_Point', {1}, {2}), 4326))",
                                        placeEntity.addressEntity.locationPoint,
                                longitude,
                                latitude
                                )
                        )// 거리 계산 추가)
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
                .orderBy(
                        Expressions.numberTemplate(
                                Double.class,
                                "function('ST_Distance', {0}, function('ST_SetSRID', function('ST_Point', {1}, {2}), 4326))",
                                placeEntity.addressEntity.locationPoint,
                                longitude,
                                latitude
                        ).asc() // 거리 기준 오름차순 정렬
                )
                .fetch();
    }
    //검색어가 있을 경우 조회
    @Override
    public List<PlaceCommand> findByVectorSearchAndQueryDsl(
            List<Long> placeIds,
            Point userLocation,
            String category,
            Size size,
            List<String> entryConditions,
            List<String> types) {

        return queryFactory
                .select(new QPlaceCommand(
                        placeEntity.id,
                        placeEntity.name,
                        placeEntity.categoryEntity.name,
                        placeEntity.photoUrl,
                        placeEntity.addressEntity.locationPoint,
                        placeEntity.addressEntity.roadAddress,
                        placeEntity.addressEntity.postalCode,
                        Expressions.numberTemplate(
                                Double.class,
                                "function('ST_Distance', {0}, function('ST_SetSRID', function('ST_Point', {1}, {2}), 4326))",
                                placeEntity.addressEntity.locationPoint,
                                userLocation.getY(),
                                userLocation.getX()
                        )
                ))
                .from(placeEntity)
                .leftJoin(placeEntity.categoryEntity, categoryEntity)
                .where(
                        placeEntity.id.in(placeIds),
                        matchesCategory(category),
                        matchesSize(size),
                        matchesEntryConditionsAnd(entryConditions),
                        matchesTypes(types)
                )
                .fetch();
    }
    // 반경 조건 (ST_Buffer + ST_Contains 활용)
    private BooleanExpression isWithinBuffer(Point userLocation, double radiusMeters) {
        return Expressions.booleanTemplate(
                "function('ST_DWithin', {0}, function('ST_SetSRID', function('ST_Point', {1}, {2}), 4326), {3}) = true",
                placeEntity.addressEntity.locationPoint, // 장소 위치
                userLocation.getY(), // 경도
                userLocation.getX(), // 위도
                radiusMeters // 반경
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
        if (size == null) {
            return null;
        }
        return placeEntity.size.in(allowSizesBySize(size));
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

