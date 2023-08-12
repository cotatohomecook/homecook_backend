package com.cotato.homecook.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponse {
    Long customerId;
    Long sellerId;
}
