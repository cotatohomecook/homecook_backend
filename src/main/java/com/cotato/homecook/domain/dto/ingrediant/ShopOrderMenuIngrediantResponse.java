package com.cotato.homecook.domain.dto.ingrediant;

import com.cotato.homecook.domain.entity.Ingrediant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopOrderMenuIngrediantResponse {
    Long ingrediantId;
    String ingrediant;

    public ShopOrderMenuIngrediantResponse(Ingrediant ingrediant) {
        ingrediantId = ingrediant.getIngrediantId();
        this.ingrediant = ingrediant.getIngrediant();
    }
}
