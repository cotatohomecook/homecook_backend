package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.bookmark.BookmarkResponse;
import com.cotato.homecook.domain.entity.Bookmark;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.repository.BookmarkRepository;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public String bookmarkShop(@PathVariable Long shopId, @PathVariable String folderName) {
        // TODO: Custom Exception으로 설정하기
        Customer customer = customerRepository.findById(329329L).orElseThrow(RuntimeException::new);
        Shop shop = shopRepository.findById(shopId).orElseThrow(RuntimeException::new);
        Bookmark bookmark = Bookmark.builder()
                .customer(customer)
                .shop(shop)
                .folderName(folderName).build();
        bookmarkRepository.save(bookmark);
        return "Bookmark Complete";
    }

    public List<BookmarkResponse> getBookmarks() {
//        String email = SecurityUtil.getEmail();
//        Customer customer = validateCustomer(email);
//        return bookmarkRepository.findAllById(customer.getid());
        return bookmarkRepository.findByCustomer_CustomerId(329329L)
                .stream()
                .map(b -> {
                    BookmarkResponse bookmarkResponse = new BookmarkResponse(b);
                    System.out.println("b.getFolderName() = " + b.getFolderName());
                    return bookmarkResponse;
                })
                .collect(Collectors.toList());
    }
}