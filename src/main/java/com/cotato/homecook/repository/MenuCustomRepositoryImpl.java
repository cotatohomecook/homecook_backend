package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Menu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.cotato.homecook.domain.entity.QMenu.menu;
import static com.cotato.homecook.domain.entity.QOrderQuantity.orderQuantity;

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
}
