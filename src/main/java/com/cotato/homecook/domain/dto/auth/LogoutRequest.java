package com.cotato.homecook.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LogoutRequest {
    String customerAccessToken;
    String sellerAccessToken;
    String refreshToken;
}
