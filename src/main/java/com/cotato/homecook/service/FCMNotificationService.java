package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.fcm.FCMNotificationRequestDto;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.SellerRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {
        Optional<Customer> customer = customerRepository.findById(requestDto.getTargetUserId());
        if (customer.isPresent()) {
            // TODO: 테스트를 위해 임의로 해놓은 것 => getPassword()가 아니라, getFirebaseToken()이어야 함
            if (customer.get().getPassword() != null) {
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody()).build();
                // TODO: 테스트를 위해 임의로 해놓은 것 => getPassword()가 아니라, getFirebaseToken()이어야 함
                Message message = Message.builder().setToken(customer.get().getPassword())
                        .setNotification(notification)
                        .build();
                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다. targetUserId = " + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알림 보내기를 실패하였습니다. targetUserId = " + requestDto.getTargetUserId();
                }
            } else {
                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId = " + requestDto.getTargetUserId();
            }
        } else {
            return "해당 유저가 존재하지 않습니다. targetUserId = " + requestDto.getTargetUserId();
        }
    }
}