package com.notx2wice.puzzlemakers.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@Configuration
@EnableJpaRepositories(basePackages = {RdsDataConfig.RDS_DOMAIN_PACKAGE}) // JpaRepository 패키지 위치 등록
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class RdsDataConfig {
    static final String RDS_DOMAIN_PACKAGE = "com.notx2wice.puzzlemakers.repository.rds";
}

