package com.cotato.homecook.domain.dto.menu;

import com.cotato.homecook.domain.dto.ingrediant.ShopOrderMenuIngrediantResponse;
import com.cotato.homecook.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ShopOrderMenuResponse {
    Long menuId;
    private String menuName;
    private String imageUrl;
    private Long price;
    private String description;
    private List<ShopOrderMenuIngrediantResponse> ingrediants;

    public ShopOrderMenuResponse(Menu menu) {
        menuId = menu.getMenuId();
        menuName = menu.getMenuName();
        imageUrl = menu.getImageUrl();
        price = menu.getPrice();
        description = menu.getDescription();
        ingrediants = getIngrediantList(menu);
    }

    private static List<ShopOrderMenuIngrediantResponse> getIngrediantList(Menu menu) {
        List<ShopOrderMenuIngrediantResponse> ingrediantList = new ArrayList<>();
        menu.getIngrediants().forEach(ingrediant -> ingrediantList.add(new ShopOrderMenuIngrediantResponse(ingrediant)));
        return ingrediantList;
    }
}
