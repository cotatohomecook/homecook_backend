package com.cotato.homecook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderHistoryCustomRepositoryImpl implements OrderHistoryCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
}
