package com.bannylog.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
    @PersistenceContext // EntityManager를 빈으로 주입할 때 사용하는 어노테이션
    public EntityManager em;

    @Bean // 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들려할 때 사용
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
