package de.zooplus.converter.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by dragan
 */
@Configuration
@EnableJpaRepositories(basePackages = "de.zooplus.converter.dao.repository",
        transactionManagerRef = DatabaseEmbeddedConfig.DB_ENTITY_TRANSACTION_MANAGER)
public class TestDatabaseEmbededConfig {

    private JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(final DataSource datasource) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(datasource);
        factoryBean.setPersistenceUnitName(DatabaseEmbeddedConfig.UNIT_NAME);
        factoryBean.setPackagesToScan("de.zooplus.converter.model.entity");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    private Properties jpaProperties() {
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.format_sql", "false");

        return jpaProperties;
    }

    @Bean(name = DatabaseEmbeddedConfig.UNIT_NAME + "_datasource")
    public DataSource datasource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).ignoreFailedDrops(true).build();
    }


    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());

        return initializer;
    }

    /**
     * This method is used to insert content from SQL Files to setup the DB. Should be overridden from subclasses.
     *
     * @return the DatabasePopulator
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

        resourceDatabasePopulator.addScript(new ClassPathResource("/db/sql/test-insert-data.sql"));
        return resourceDatabasePopulator;
    }

    @Bean(name = DatabaseEmbeddedConfig.DB_ENTITY_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(final PlatformTransactionManager transactionManager) {
        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate;
    }
}
