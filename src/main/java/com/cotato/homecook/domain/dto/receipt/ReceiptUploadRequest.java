package com.cotato.homecook.domain.dto.receipt;

import com.cotato.homecook.domain.entity.Receipt;
import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptUploadRequest {
    MultipartFile receiptImage;
}
