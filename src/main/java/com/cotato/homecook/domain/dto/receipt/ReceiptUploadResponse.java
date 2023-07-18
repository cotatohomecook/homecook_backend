package com.cotato.homecook.domain.dto.receipt;

import com.cotato.homecook.domain.entity.Receipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptUploadResponse {
    String imageUrl;
    public static ReceiptUploadResponse toDto(Receipt receipt) {
        return ReceiptUploadResponse.builder()
                .imageUrl(receipt.getImageUrl())
                .build();
    }
}
