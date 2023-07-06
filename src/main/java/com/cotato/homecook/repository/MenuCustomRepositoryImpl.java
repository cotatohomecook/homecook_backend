package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.menu.ShopDailyBestMenuResponse;
import com.cotato.homecook.domain.entity.Menu;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.cotato.homecook.domain.entity.QMenu.menu;
import static com.cotato.homecook.domain.entity.QOrderQuantity.orderQuantity;
import static com.cotato.homecook.domain.entity.QOrderHistory.orderHistory;

@Repository
@RequiredArgsConstructor
public class MenuCustomRepositoryImpl implements MenuCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Menu findBestMenuNameByShopId(Long shopId) {
        return jpaQueryFactory
                .select(menu)
                .from(menu)
                .leftJoin(orderQuantity).on(menu.menuId.eq(orderQuantity.menu.menuId))
                .where(menu.shop.shopId.eq(shopId))
                .groupBy(menu.menuId)
                .orderBy(orderQuantity.menu.menuId.count().desc())
                .fetchFirst();
    }

    @Override
    public ShopDailyBestMenuResponse findDailyBestMenuByDateAndShopId(LocalDate date, Long shopId) {
        return jpaQueryFactory.select(Projections.constructor(ShopDailyBestMenuResponse.class,
                        orderQuantity.menu, orderQuantity.quantity.sum()))
                .from(orderQuantity)
                .join(orderQuantity.orderHistory, orderHistory)
                .where(orderHistory.shop.shopId.eq(shopId)
                        .and(orderHistory.orderedAt.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay())))
                .groupBy(orderQuantity.menu)
                .orderBy(orderQuantity.quantity.sum().desc()).fetchFirst();
    }
}
