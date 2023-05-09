package com.cotato.homecook.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cotato.homecook.domain.entity.QOrderHistory.orderHistory;
import static com.cotato.homecook.domain.entity.QShop.shop;

@Repository
@RequiredArgsConstructor
public class CustomShopRepositoryImpl implements CustomShopRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JPASQLQuery jpaSqlQuery;
//    public CustomShopRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.jpaQueryFactory = jpaQueryFactory;
//
//    }

    @Override
    public List<ShopRankResponse> findTop10ShopsByOrderCount(double userLatitude, double userLongitude) {
        return jpaSqlQuery
                .select(Projections.constructor(ShopRankResponse.class,
                        shop.shopId, shop.shopName
                        , orderHistory.count().as("orderCount")
                        , SQLExpressions.rowNumber().over().orderBy(orderHistory.count().desc()).as("ranking")
                ))
                .from(shop)
                .join(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .where(Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                        Expressions.stringTemplate("POINT({0}, {1})",
                                userLongitude,
                                userLatitude
                        ),
                        Expressions.stringTemplate("POINT({0}, {1})",
                                shop.longitude,
                                shop.latitude
                        )
                ).loe(String.valueOf(3000)))
                .groupBy(shop.shopId)
                .orderBy(orderHistory.count().desc())
                .fetch();
    }
}
