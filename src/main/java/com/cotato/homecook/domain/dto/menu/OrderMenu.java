package com.cotato.homecook.domain.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderMenu {
    Long menuId;
    Long quantity;
}
