package com.ureca.gate.favorites.infrastructure.jpaadapter;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.gate.favorites.infrastructure.command.PlaceReviewInfo;
import com.ureca.gate.favorites.infrastructure.command.QPlaceReviewInfo;
import com.ureca.gate.place.infrastructure.command.FavoritesCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.ureca.gate.place.infrastructure.jpaadapter.entity.QPlaceEntity.placeEntity;
import static com.ureca.gate.review.infrasturcture.jpaadapter.entity.QReviewEntity.reviewEntity;
import static com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy.QFavoritesEntity.favoritesEntity;




import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryCustomImpl implements FavoritesRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<PlaceReviewInfo> getAllFavorites(FavoritesCondition favoritesCondition) {
        return queryFactory
                .select(new QPlaceReviewInfo(
                        placeEntity.id,
                        placeEntity.name,
                        placeEntity.addressEntity.locationPoint,
                        placeEntity.addressEntity.roadAddress,
                        placeEntity.photoUrl,
                        reviewEntity.id.count().intValue(),
                        Expressions.numberTemplate(Double.class, "COALESCE(ROUND({0}, 1), 0)", reviewEntity.starRate.avg())
                ))
                .from(favoritesEntity)
                .join(favoritesEntity.placeEntity, placeEntity)
                .leftJoin(placeEntity.reviewEntityList, reviewEntity)
                .where(
                        favoritesEntity.memberEntity.id.eq(favoritesCondition.getMemberId()),
                        sizeIn(favoritesCondition.getAllowSizes()),
                        cityIdEq(favoritesCondition.getCityId())
                )
                .groupBy(placeEntity.id)// 장소별로 그룹화하여 평균 별점 계산
                .fetch();
    }

    private BooleanExpression cityIdEq(Long cityId) {
        return cityId == null || cityId < 1L ? null : placeEntity.addressEntity.cityEntity.id.eq(cityId);
    }

    private BooleanExpression sizeIn(List<String> allowSizes) {
        return allowSizes == null || allowSizes.isEmpty() ? null : placeEntity.size.in(allowSizes);
    }
}
