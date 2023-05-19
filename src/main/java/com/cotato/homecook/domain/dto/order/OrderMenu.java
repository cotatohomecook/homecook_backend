package com.cotato.homecook.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderMenu {
    Long menuId;
    Long quantity;
}
