package com.cotato.homecook.domain.dto.shop;

import java.util.function.DoubleBinaryOperator;

public interface ShopMapResponseInterface {
    long getShopId();
    String getShopName();
    String getImageUrl();
    double getLatitude();
    double getLongitude();
    double getRating();
}
