package com.cotato.homecook.controller;

import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.repository.*;
import com.cotato.homecook.utils.DummyDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dummy-data")
public class DummyDataController {
    private final DummyDataGenerator dummyDataGenerator;
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final TagRepository tagRepository;
    private final IngrediantRepository ingrediantRepository;
    private final OrderQuantityRepository orderQuantityRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ReceiptRepository receiptRepository;

    @GetMapping("/seller-shop")
    public String insertSellerDummyData() {
        for (int i = 0; i < 50; i++) {
            Seller seller = Seller.builder()
                    .sellerName(dummyDataGenerator.generateSellerName())
                    .build();
            sellerRepository.save(seller);
            Shop shop = Shop.builder()
                    .shopName(dummyDataGenerator.generateShopName())
                    .longitude(dummyDataGenerator.generateCoordinate("longitude"))
                    .latitude(dummyDataGenerator.generateCoordinate("latitude"))
                    .category(dummyDataGenerator.generateCategory())
                    .imageUrl("https://homecook-bucket.s3.ap-northeast-2.amazonaws.com/static/b7f977bc-843e-4a82-8b3e-332e7ef5aefcpngfind.com-mysql-logo-transparent-png-744402.png")
                    .seller(seller)
                    .build();

            shopRepository.save(shop);
        }
        return "success";
    }

    @GetMapping("/menu")
    public String insertMenuDummyData() {
        List<Shop> shopList = shopRepository.findAll();
        for (Shop shop : shopList) {
            for (int i = 0; i < 2; i++) {
                Menu menu = Menu.builder()
                        .price(dummyDataGenerator.generateRandomPrice())
                        .menuName(dummyDataGenerator.generateRandomString("메뉴"))
                        .description(dummyDataGenerator.generateRandomString("메뉴설명"))
                        .imageUrl("https://homecook-bucket.s3.ap-northeast-2.amazonaws.com/static/%EC%A0%9C%EB%AA%A9+%EC%97%86%EC%9D%8C.png")
                        .shop(shop)
                        .build();
                menuRepository.save(menu);
            }
        }
        return "success";
    }

    @GetMapping("/tag-ingrediant")
    public String insertTagIngrediantDummyData() {
        List<Menu> menuList = menuRepository.findAll();
        for (Menu menu : menuList) {
            for (int i = 0; i < 2; i++) {
                Tag tag = Tag.builder()
                        .tag(dummyDataGenerator.generateRandomString("태그"))
                        .menu(menu)
                        .build();
                Ingrediant ingrediant = Ingrediant.builder()
                        .ingrediant(dummyDataGenerator.generateRandomString("재료"))
                        .menu(menu)
                        .build();
                tagRepository.save(tag);
                ingrediantRepository.save(ingrediant);
            }
        }
        return "success";
    }

    @GetMapping("/customer")
    public String insertCustomerDummyData() {
        for (int i = 0; i < 50; i++) {
            Customer customer = Customer.builder()
                    .customerName(dummyDataGenerator.generateRandomString("구매자"))
                    .build();
            customerRepository.save(customer);
        }
        return "success";
    }

    @GetMapping("/order-history")
    public String insertOrderHistoryDummyData() {
        List<Customer> customerList = customerRepository.findAll();
        List<Shop> shopList = shopRepository.findAll();
        for (int i = 0; i < 100; i++) {
            OrderHistory orderHistory = OrderHistory.builder()
                    .customer(customerList.get(dummyDataGenerator.generateRandomNumber(0, 49)))
                    .shop(shopList.get(dummyDataGenerator.generateRandomNumber(0, 49)))
                    .isCompleted(true)
                    .isDeleted(false)
                    .build();
            orderHistoryRepository.save(orderHistory);
        }
        return "success";
    }

    @GetMapping("/order-quantity")
    public String insertOrderQuantityDummyData() {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        for (OrderHistory orderHistory : orderHistoryList) {
            List<Menu> menus = orderHistory.getShop().getMenus();
            for (int i = 0; i < dummyDataGenerator.generateRandomNumber(1, 2); i++) {
                OrderQuantity orderQuantity = OrderQuantity.builder()
                        .menu(menus.get(i))
                        .orderHistory(orderHistory)
                        .quantity((long) dummyDataGenerator.generateRandomNumber(1, 5))
                        .build();
                orderQuantityRepository.save(orderQuantity);
            }
        }
        return "success";
    }

    @GetMapping("/review")
    public String insertReviewDummyData() {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        for (OrderHistory orderHistory : orderHistoryList) {
            if (dummyDataGenerator.generateRandomNumber(0, 1) == 1) {
                Review review = Review.builder()
                        .content(dummyDataGenerator.generateRandomString("리뷰 내용"))
                        .customer(orderHistory.getCustomer())
                        .orderHistory(orderHistory)
                        .shop(orderHistory.getShop())
                        .is_deleted(dummyDataGenerator.generateRandomNumber(0,1) == 0 ? false : true)
                        .rating(dummyDataGenerator.generateRandomNumber(1, 10) * 0.5)
                        .imageUrl("https://homecook-bucket.s3.ap-northeast-2.amazonaws.com/static/%EB%A6%AC%EB%B7%B0+%EC%82%AC%EC%A7%84.png")
                        .build();

                reviewRepository.save(review);
            }
        }
        return "success";
    }

    @GetMapping("/bookmark")
    public String insertBookmarkDummyData() {
        List<Customer> customerList = customerRepository.findAll();
        List<Shop> shopList = shopRepository.findAll();
        for (Customer customer : customerList) {
            if (dummyDataGenerator.generateRandomNumber(0, 1) == 1) {
                Bookmark bookmark = Bookmark.builder()
                        .customer(customer)
                        .shop(shopList.get(dummyDataGenerator.generateRandomNumber(0, 49)))
                        .folderName(dummyDataGenerator.generateRandomString("폴더 이름"))
                        .build();
                bookmarkRepository.save(bookmark);
            }
        }
        return "success";
    }

    @GetMapping("/receipt")
    public String insertReceiptDummyData() {
        List<Shop> shopList = shopRepository.findAll();
        for (Shop shop : shopList) {
            Receipt receipt = Receipt.builder()
                    .imageUrl("https://homecook-bucket.s3.ap-northeast-2.amazonaws.com/static/%EC%98%81%EC%88%98%EC%A6%9D+%EC%82%AC%EC%A7%84.png")
                    .shop(shop)
                    .build();
            receiptRepository.save(receipt);
        }
        return "success";
    }

    @GetMapping("/test")
    public String test() {
        Optional<OrderHistory> byId = orderHistoryRepository.findById(101L);
        LocalDateTime orderedAt = byId.get().getOrderedAt();
        System.out.println("orderedAt = " + orderedAt);
        return orderedAt.toString();
    }
}
