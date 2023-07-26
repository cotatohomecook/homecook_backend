package com.cotato.homecook.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReissueResponse {
    String accessToken;
}
