package de.zooplus.converter.dao.config;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.lf5.util.Resource;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * This is the TPC database test configuration. It is using an embedded H2 instance. Schema is created automatically by using "create". Do not use
 * this configuration with a real database.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.zooplus.converter.dao.repository", entityManagerFactoryRef = DatabaseEmbeddedConfig.DB_ENTITY_MANAGER_FACOTRY,
        transactionManagerRef = DatabaseEmbeddedConfig.DB_ENTITY_TRANSACTION_MANAGER)
public class DatabaseEmbeddedConfig {

    /**
     * The name of the entity manager factory.
     */
    public static final String DB_ENTITY_MANAGER_FACOTRY = DatabaseEmbeddedConfig.UNIT_NAME + "_entityManagerFactory";

    /**
     * The name of the transaction manager.
     */
    public static final String DB_ENTITY_TRANSACTION_MANAGER = DatabaseEmbeddedConfig.UNIT_NAME + "_transactionManager";

    public static final String UNIT_NAME = "converterDbUnit";

    @Value("${DATABASE_USER}")
    private String username;

    @Value("${DATABASE_PASSWORD}")
    private String password;

    @Value("${DATABASE_START_AS_SERVER}")
    private boolean startAsServer;

    @Value("${DATABASE_FILE_NAME}")
    private String dbFileName;

    @Value("${DATABASE_SERVER_PORT}")
    private int dbServerPort;


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

    @Bean(name = DB_ENTITY_MANAGER_FACOTRY)
    public EntityManagerFactory entityManagerFactory(@Qualifier(UNIT_NAME + "_datasource") final DataSource datasource) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(datasource);
        factoryBean.setPersistenceUnitName(UNIT_NAME);
        factoryBean.setPackagesToScan("de.zooplus.converter.model.entity");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    private Properties jpaProperties() {
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.format_sql", "false");

        return jpaProperties;
    }

    @Bean(name = UNIT_NAME + "_datasource")
    public DataSource datasource() {
         /*
     * This is only in memory.
     */
        // return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).ignoreFailedDrops(true).build();

        String dapAppDataDir = "./db/appdata";

        final JdbcDataSource dataSource = new JdbcDataSource();
        if (startAsServer) {
            startDBServer(dapAppDataDir);
            dataSource.setURL("jdbc:h2:tcp://localhost:" + dbServerPort + "/" + dbFileName + ";AUTO_SERVER=TRUE");
            dataSource.setUser(username);
            dataSource.setPassword(password);
            System.out.println("START EMBEDDED DATABASE AS SERVER");
        } else {
            final String dbFile = dapAppDataDir + "/" + dbFileName;
            dataSource.setURL("jdbc:h2:" + dbFile + ";AUTO_SERVER=TRUE");
            dataSource.setUser(username);
            dataSource.setPassword(password);
            System.out.println("START EMBEDDED DATABASE AS FILE");
        }

        return dataSource;
    }

    /**
     * Initializes the database with the content of the below used SQL files. Note: Standard initialization is not working due to problems with the
     * database structure generation.
     *
     * @param dataSource The used datasource
     * @return The initializer
     */

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDatabasePopulator(databasePopulator());
        initializer.setDataSource(dataSource);

        return initializer;
    }

    /**
     * This method is used to insert content from SQL Files to setup the DB. Should be overridden from subclasses.
     *
     * @return the DatabasePopulator
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

//            resourceDatabasePopulator.addScript(new PathResource("db/sql/insert-data.sql"));
        return resourceDatabasePopulator;
    }



    @Bean(name = DB_ENTITY_TRANSACTION_MANAGER)
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

    /**
     * Is only used if we want to access the embedded db from outside. This will create a db file.
     */
    private void startDBServer(final String dapAppDataDir) {
        try {
            final Server server = Server.createTcpServer("-tcpPort", Integer.toString(dbServerPort), "-tcpAllowOthers", "-baseDir", dapAppDataDir);
            server.start();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }
}