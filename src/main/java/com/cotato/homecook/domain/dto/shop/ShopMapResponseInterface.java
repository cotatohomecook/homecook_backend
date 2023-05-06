package com.cotato.homecook.domain.dto.shop;

import javax.persistence.Column;

public interface ShopMapResponseInterface {
    long getShop_Id();

    String getShop_Name();
    String getImage_Url();
    double getLatitude();
    double getLongitude();
    double getRating();
    long getReviewCount();
}
