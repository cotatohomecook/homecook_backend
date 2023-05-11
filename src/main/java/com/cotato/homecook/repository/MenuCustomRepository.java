package com.cotato.homecook.repository;

import com.cotato.homecook.domain.entity.Menu;

public interface MenuCustomRepository {
    Menu findBestMenuNameByShopId(Long shopId);
}
