package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.bookmark.BookmarkResponse;
import com.cotato.homecook.domain.entity.Bookmark;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.repository.BookmarkRepository;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final ValidateService validateService;
    private final BookmarkRepository bookmarkRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;


    // 식당 즐겨찾기 설정
    @Transactional
    public String bookmarkShop(@PathVariable Long shopId, @PathVariable String folderName) {
        // TODO: Custom Exception으로 설정하기
        // TODO: 회원가입 구현 후 customer도 validate로 가져오기
        Customer customer = customerRepository.findById(329329L).orElseThrow(RuntimeException::new);
        Shop shop = validateService.validateShop(shopId);
        Bookmark bookmark = Bookmark.builder()
                .customer(customer)
                .shop(shop)
                .folderName(folderName).build();
        bookmarkRepository.save(bookmark);
        return "Bookmark Complete";
    }

    // 자신이 즐겨찾기한 상점들의 정보 조회
    public List<BookmarkResponse> getBookmarks() {
        // TODO: 로그인 구현 후 사용자 정보를 토큰에서 바로 뽑아오도록 설정하기
//        String email = SecurityUtil.getEmail();
//        Customer customer = validateCustomer(email);
        return bookmarkRepository.findByCustomer_CustomerId(329329L)
                .stream()
                .map(BookmarkResponse::new)
                .map(this::getBestMenuByShopDto)
                .collect(Collectors.toList());
    }

    // 이미 즐겨찾기 되어있던 상점을 즐겨찾기에서 삭제
    public String deleteBookmark(Long bookmarkId) {
        Bookmark bookmark = validateService.validateBookmark(bookmarkId);
        bookmarkRepository.delete(bookmark);
        return "즐겨찾기 삭제 완료";
    }

    // 즐겨찾기 폴더 전체 삭제


    private BookmarkResponse getBestMenuByShopDto(BookmarkResponse bookmarkResponse) {
        // 북마크 확인 코드 추가 필요함
        // get(0)에서 예외 throw 하는 코드 필요
        Menu bestMenu = menuRepository.findBestMenuNameByShopId(bookmarkResponse.getShopId());
        bookmarkResponse.setBestMenuName(bestMenu.getMenuName());
        return bookmarkResponse;
    }

    public String deleteBookmarksByFolderName(String bmFolderName) {
        return "즐겨찾기 폴더 + " + bmFolderName + " 삭제 완료";
    }
}