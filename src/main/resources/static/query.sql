# SELECT s.shop_id,
#        s.shop_name,
#        s.latitude,
#        s.longitude,
#        COUNT(o.order_history_id)                                                                       AS order_count,
#        FLOOR(ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude))) AS distance
# FROM shop s
#          JOIN order_history o ON s.shop_id = o.shop_id
# WHERE o.is_completed = true
# GROUP BY s.shop_id
# ORDER BY order_count DESC
# LIMIT 3

SELECT s.shop_id                                                   AS shopId,
       s.shop_name                                                 AS shopName,
       COUNT(o.order_history_id)                                   AS orderCount,
       ROW_NUMBER() OVER (ORDER BY COUNT(o.order_history_id) DESC) AS ranking
FROM shop s
         JOIN order_history o ON s.shop_id = o.shop_id
WHERE o.is_completed = true
  AND ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 500
GROUP BY s.shop_id
ORDER BY orderCount DESC
LIMIT 10

SELECT m.*, COUNT(oq.menu_id) as count
FROM menu m
         LEFT JOIN order_quantity oq ON m.menu_id = oq.menu_id
WHERE m.shop_id = :shopId
GROUP BY m.menu_id
ORDER BY count DESC @Query("SELECT m FROM Menu m " +
            "WHERE m.shop.shopId = :shopId " +
            "ORDER BY (SELECT COUNT(oq) FROM OrderQuantity oq WHERE oq.menu = m) DESC")


SELECT *,
       coalesce((SELECT AVG(r.rating) FROM review r
                                               INNER JOIN order_history oh ON r.order_history_id = oh.order_history_id
                 WHERE oh.shop_id = s.shop_id),0) as rating
FROM shop s
WHERE ST_Distance_Sphere(point(:userLongitude, :userLatitude), point(s.longitude, s.latitude)) <= 3000