// Copyright (c) 2019 Mastercard, VocaLink Ltd
package com.endava.internship.codesolver.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.endava.internship.codesolver.CodeSolverApplication;
import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    public static final String PERSISTENCE_UNIT = "CODESOLVER";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder,
            @Qualifier("regDataSource") final DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(CodeSolverApplication.class)
                .persistenceUnit(PERSISTENCE_UNIT)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("regDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("datasource.hikari")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public com.vladmihalcea.flexypool.config.Configuration<HikariDataSource> configuration(final HikariDataSource poolingDataSource,
            FlexyPoolProperties properties) {
        return new com.vladmihalcea.flexypool.config.Configuration.Builder<>(
                properties.getUniqueId(),
                poolingDataSource,
                HikariCPPoolAdapter.FACTORY
        ).build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public FlexyPoolDataSource regDataSource(final com.vladmihalcea.flexypool.config.Configuration<HikariDataSource> configuration,
            FlexyPoolProperties properties) {
        return new FlexyPoolDataSource<HikariDataSource>(configuration,
                new IncrementPoolOnTimeoutConnectionAcquiringStrategy.Factory(properties.getMaxOverflowPoolSize()),
                new RetryConnectionAcquiringStrategy.Factory(properties.getRetryAttempts())
        );
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
