package com.cotato.homecook.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLTemplates mysqlTemplates() {
        return MySQLTemplates.builder().build();
    }

    @Bean
    public JPASQLQuery jpasqlQuery() {
        return new JPASQLQuery(entityManager, mysqlTemplates());
    }
}
