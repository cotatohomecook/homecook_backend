package com.cotato.homecook.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    // 가게랑 many to one , menu가 외래키 저장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // 태그랑 one to many
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    List<Tag> tags = new ArrayList<>();

    // 재료량 one to many
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    List<Ingrediant> ingrediants  = new ArrayList<>();
    private String imageUrl;
    private Long price;
    private String description;
    private String category;

}
