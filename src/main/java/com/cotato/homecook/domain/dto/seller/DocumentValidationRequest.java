package com.cotato.homecook.domain.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentValidationRequest {
    String companyName;
    String licenseNumber;
    String businessCategory;
    //yyyy-MM-dd
    String permissionDate;
    String area;
}
