package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.menu.ShopDailyBestMenuResponse;
import com.cotato.homecook.domain.entity.Menu;

import java.time.LocalDate;

public interface MenuCustomRepository {
    Menu findBestMenuNameByShopId(Long shopId);
    ShopDailyBestMenuResponse findDailyBestMenuByDateAndShopId(LocalDate date, Long shopId);
}
