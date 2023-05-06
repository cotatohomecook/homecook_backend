package com.cotato.homecook.domain.dto.shop;

public interface ShopDefaultResponseInterface {
    long getShop_Id();
    String getShop_Name();
    String getImage_Url();
    double getLatitude();
    double getLongitude();
    double getRating();
    Long getReviewCount();
    long getDistance();
    long getRanking();
    long getOrderCount();
}
