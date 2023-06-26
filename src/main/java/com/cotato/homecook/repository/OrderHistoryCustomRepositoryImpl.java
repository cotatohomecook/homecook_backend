package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cotato.homecook.domain.entity.QMenu.menu;
import static com.cotato.homecook.domain.entity.QOrderHistory.orderHistory;
import static com.cotato.homecook.domain.entity.QOrderQuantity.orderQuantity;
import static com.cotato.homecook.domain.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class OrderHistoryCustomRepositoryImpl implements OrderHistoryCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderHistorySellerResponse> findAllSellerOrderHistoryByShopId(Long shopId) {
//        return jpaQueryFactory
//                .select(Projections.constructor(OrderHistorySellerResponse.class,
//                        orderHistory.orderHistoryId,
//                        menu.menuName,
//                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0)", review.rating).as("rating")
//                ))
//                .from(orderHistory)
//                .leftJoin(orderHistory.orderQuantities, orderQuantity)
//                .leftJoin(orderQuantity.menu, menu)
//                .leftJoin(orderHistory.review, review)
//                .where(orderHistory.shop.shopId.eq(shopId))
//                .orderBy(orderHistory.orderHistoryId.desc() ,menu.price.desc().nullsLast())
//                .groupBy(orderHistory.orderHistoryId)
//                .fetch();
//    }
    return jpaQueryFactory
                .select(Projections.constructor(OrderHistorySellerResponse.class,
                        orderHistory.orderHistoryId,
                        menu.menuName,
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0)", review.rating).as("rating")
                ))
                .from(orderHistory)
                .leftJoin(orderHistory.orderQuantities, orderQuantity)
                .leftJoin(orderQuantity.menu, menu)
                .leftJoin(orderHistory.review, review)
                .where(orderHistory.shop.shopId.eq(shopId))
                .orderBy(orderHistory.orderHistoryId.desc() ,menu.price.desc().nullsLast())
                .groupBy(orderHistory.orderHistoryId)
                .fetch();
    }
}
