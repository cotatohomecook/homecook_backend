package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.repository.CustomShopRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cotato.homecook.domain.entity.QOrderHistory.orderHistory;
import static com.cotato.homecook.domain.entity.QReview.review;
import static com.cotato.homecook.domain.entity.QShop.shop;
import static com.cotato.homecook.domain.entity.QMenu.menu;

@Repository
@RequiredArgsConstructor
public class CustomShopRepositoryImpl implements CustomShopRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ShopRankResponse> findTop10ShopsByOrderCount(double userLatitude, double userLongitude) {
        return jpaQueryFactory
                .select(Projections.constructor(ShopRankResponse.class,
                        shop.shopId, shop.shopName, orderHistory.count().as("orderCount")))
                .from(shop)
                .join(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .where(isIn3KM(userLatitude, userLongitude))
                .groupBy(shop.shopId)
                .orderBy(orderHistory.count().desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<ShopBestMenuResponse> findRadndom10Shops(double userLatitude, double userLongitude) {
        return getShopBestMenuReponseQuerySelect2Join(userLatitude, userLongitude)
                .where(isIn3KM(userLatitude, userLongitude))
                .groupBy(shop.shopId)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(10)
                .fetch();
    }


    @Override
    public List<ShopMapResponse> findAllNearShops(double userLatitude, double userLongitude) {
        return jpaQueryFactory
                .select(Projections.constructor(ShopMapResponse.class,
                        shop.shopId, shop.shopName, shop.imageUrl, shop.latitude, shop.longitude,
                        Expressions.numberTemplate(Double.class, "COALESCE(ROUND(AVG({0}), 1), 0)", review.rating).as("rating"),
                        Expressions.numberTemplate(Long.class, "COALESCE(COUNT({0}), 0)", review.reviewId).as("reviewCount"),
                        Expressions.numberTemplate(Long.class, "FLOOR(ST_Distance_Sphere(point({0}, {1}), point({2}, {3})))",
                                userLongitude, userLatitude, shop.longitude, shop.latitude).as("distance")))
                .from(shop)
                .leftJoin(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .leftJoin(review).on(review.orderHistory.orderHistoryId.eq(orderHistory.orderHistoryId))
                .where(isIn3KM(userLatitude, userLongitude))
                .groupBy(shop.shopId)
                .fetch();
    }

    @Override
    public Page<ShopBestMenuResponse> findAllByCategoryByOrderCount(double userLatitude, double userLongitude, String category, Pageable pageable) {
        List<ShopBestMenuResponse> dtoList = getShopBestMenuReponseQuerySelect2Join(userLatitude, userLongitude)
                .where(isIn3KM(userLatitude, userLongitude), eqCategory(category))
                .groupBy(shop.shopId)
                .orderBy(orderHistory.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Shop> countQuery = jpaQueryFactory.selectFrom(shop)
                .leftJoin(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .leftJoin(review).on(review.orderHistory.orderHistoryId.eq(orderHistory.orderHistoryId))
                .where(isIn3KM(userLatitude, userLongitude), eqCategory(category))
                .groupBy(shop.shopId);

        return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<ShopBestMenuResponse> findAllByShopName(double userLatitude, double userLongitude, String shopName, String orderBy, Pageable pageable) {
        List<ShopBestMenuResponse> dtoList = getShopBestMenuReponseQuerySelect2Join(userLatitude, userLongitude)
                .where(isIn3KM(userLatitude, userLongitude), containsShopName(shopName))
                .groupBy(shop.shopId)
                .orderBy(getOrderByExpression(orderBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Shop> countQuery = getSearchByShopNameCountQuery(userLatitude, userLongitude, shopName);

        return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<ShopBestMenuResponse> findAlLBYMenuName(double userLatitude, double userLongitude, String menuName, String orderBy, Pageable pageable) {
        List<ShopBestMenuResponse> dtoList = getShopBestMenuReponseQuerySelect2Join(userLatitude, userLongitude)
                .leftJoin(menu).on(menu.shop.shopId.eq(shop.shopId))
                .where(isIn3KM(userLatitude, userLongitude), containsMenuName(menuName))
                .groupBy(shop.shopId)
                .orderBy(getOrderByExpression(orderBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Shop> countQuery = getSearchByMenuNameCountQuery(userLatitude, userLongitude, menuName);

        return PageableExecutionUtils.getPage(dtoList, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression eqCategory(String category) {
        if (category.equals("통합")) {
            return Expressions.asBoolean(true).isTrue();
        } else {
            return shop.category.eq(category);
        }
    }

    private BooleanExpression containsShopName(String shopName) {
        return shop.shopName.contains(shopName);
    }

    private BooleanExpression containsMenuName(String menuName) {
        return menu.menuName.contains(menuName);
    }

    private BooleanExpression isIn3KM(double userLatitude, double userLongitude) {
        return Expressions.booleanTemplate("ST_Distance_Sphere(point({0}, {1}), point({2}, {3})) <= 3000",
                userLongitude, userLatitude, shop.longitude, shop.latitude);
    }

    private JPAQuery<Shop> getSearchByShopNameCountQuery(double userLatitude, double userLongitude, String shopName) {
        return jpaQueryFactory.selectFrom(shop)
                .leftJoin(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .leftJoin(review).on(review.orderHistory.orderHistoryId.eq(orderHistory.orderHistoryId))
                .where(isIn3KM(userLatitude, userLongitude), containsShopName(shopName))
                .groupBy(shop.shopId);
    }

    private JPAQuery<Shop> getSearchByMenuNameCountQuery(double userLatitude, double userLongitude, String menuName) {
        return jpaQueryFactory.selectFrom(shop)
                .leftJoin(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .leftJoin(review).on(review.orderHistory.orderHistoryId.eq(orderHistory.orderHistoryId))
                .leftJoin(menu).on(menu.shop.shopId.eq(shop.shopId))
                .where(isIn3KM(userLatitude, userLongitude), containsMenuName(menuName))
                .groupBy(shop.shopId);
    }

    private JPAQuery<ShopBestMenuResponse> getShopBestMenuReponseQuerySelect2Join(double userLatitude, double userLongitude) {
        return jpaQueryFactory
                .select(Projections.constructor(ShopBestMenuResponse.class,
                        shop.shopId, shop.shopName, shop.imageUrl,
                        Expressions.numberTemplate(Double.class, "COALESCE(ROUND(AVG({0}), 1), 0)", review.rating).as("rating"),
                        Expressions.numberTemplate(Long.class, "COALESCE(COUNT({0}), 0)", review.reviewId).as("reviewCount"),
                        Expressions.numberTemplate(Long.class, "FLOOR(ST_Distance_Sphere(point({0}, {1}), point({2}, {3})))",
                                userLongitude, userLatitude, shop.longitude, shop.latitude).as("distance")))
                .from(shop)
                .leftJoin(orderHistory).on(shop.shopId.eq(orderHistory.shop.shopId))
                .leftJoin(review).on(review.orderHistory.orderHistoryId.eq(orderHistory.orderHistoryId));
    }

    private OrderSpecifier<Long> getOrderByExpression(String orderBy) {
        switch (orderBy) {
            case "orderCount":
                return orderHistory.count().desc();
            case "distance":
                return Expressions.numberTemplate(Long.class, "distance").asc();
            case "reviewCount":
                return Expressions.numberTemplate(Long.class, "reviewCount").desc();
            default:
                throw new IllegalArgumentException("Invalid orderBy parameter: " + orderBy);
        }
    }

}
