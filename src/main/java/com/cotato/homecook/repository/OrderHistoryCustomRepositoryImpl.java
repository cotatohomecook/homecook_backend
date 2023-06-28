package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.order.OrderHistorySellerResponse;
import com.cotato.homecook.domain.entity.Shop;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public List<OrderHistorySellerResponse> findAllSellerOrderHistoryByShopId(Shop shop, String status) {
//        return jpaQueryFactory
//                .select(Projections.constructor(OrderHistorySellerResponse.class,
//                        orderHistory.orderHistoryId,
//                        menu.count(),
//                        menu.menuName,
//                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0)", review.rating).as("rating"),
//                        orderHistory.orderedAt
//                ))
//                .from(orderHistory)
//                .leftJoin(orderHistory.orderQuantities, orderQuantity)
//                .leftJoin(orderQuantity.menu, menu)
//                .leftJoin(orderHistory.review, review)
//                .where(orderHistory.shop.eq(shop), getStatus(status))
//                .orderBy(orderHistory.orderedAt.desc(), menu.price.desc().nullsLast())
//                .groupBy(orderHistory.orderHistoryId)
//                .fetch();
//        return jpaQueryFactory
//                .select(Projections.constructor(OrderHistorySellerResponse.class,
//                        orderHistory.orderHistoryId,
////                        menu.count(),
//                        menu.menuName,
//                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0)", review.rating).as("rating"),
//                        orderHistory.orderedAt
//                ))
//                .from(orderHistory)
//                .leftJoin(orderHistory.orderQuantities, orderQuantity)
//                .leftJoin(orderQuantity.menu, menu)
//                .leftJoin(orderHistory.review, review)
//                .where(orderHistory.shop.eq(shop), getStatus(status), orderQuantity.menu.price.eq(
//                        JPAExpressions.select(menu.price.max())
//                                .from(menu)
//                                .where(orderQuantity.menu.eq(menu))
//                ))
//                .orderBy(orderHistory.orderedAt.desc(), menu.price.desc().nullsLast())
////                .groupBy(orderHistory.orderHistoryId)
//                .fetch();
        return jpaQueryFactory
                .select(Projections.constructor(OrderHistorySellerResponse.class,
                        orderHistory.orderHistoryId,
//                        orderQuantity.menu.count(),
                        menu.menuName,
                        Expressions.numberTemplate(Double.class, "COALESCE({0}, 0)", review.rating).as("rating"),
                        orderHistory.orderedAt
                ))
                .from(orderHistory)
                .leftJoin(orderHistory.orderQuantities, orderQuantity)
                .leftJoin(orderQuantity.menu, menu)
                .leftJoin(orderHistory.review, review)
                .where(orderHistory.shop.eq(shop), getStatus(status), menu.price.eq(JPAExpressions
                        .select(menu.price.max())
                        .from(orderQuantity)
                        .leftJoin(orderQuantity.menu, menu)
                        .where(orderQuantity.orderHistory.eq(orderHistory))))
                .orderBy(orderHistory.orderedAt.desc(), menu.price.desc().nullsLast())
                .groupBy(orderHistory.orderHistoryId)
                .fetch();
    }

    private BooleanExpression getStatus(String status) {
        if (status.equals("completed"))
            return orderHistory.isCompleted.eq(true);
        else if (status.equals("inDelivery"))
            return orderHistory.isCompleted.eq(false);
        else
            throw new IllegalArgumentException("Invalid parameter");
    }
}