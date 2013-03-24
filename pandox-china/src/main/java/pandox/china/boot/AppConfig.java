package pandox.china.boot;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import pandox.china.boot.util.BasePropertyConfigurer;
import pandox.china.boot.util.NameGenerator;

/**
 * Classe de configuração Spring MVC 3
 */
@Configuration
@PropertySource("classpath:config.properties")
@ImportResource("classpath:spring-security.xml")
@ComponentScan(nameGenerator = NameGenerator.class, basePackages = "pandox", excludeFilters = @Filter(Configuration.class))
@EnableTransactionManagement
@EnableJpaRepositories("pandox.china.repo")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

	private static Logger log = Logger.getLogger(AppConfig.class);

	private static HashMap<String, Object> velocityProperties = new HashMap<String, Object>();

	@Bean
	public static final BasePropertyConfigurer propertyConfigurer() {
		BasePropertyConfigurer config = new BasePropertyConfigurer();
		config.setIgnoreResourceNotFound(false);
		Resource location = new ClassPathResource("config.properties");
		config.setLocation(location);
		return config;
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		log.info("Configurando [dataSource]...");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		// jdbc:mysql://kmweb.ckx7dqi7gxfq.us-east-1.rds.amazonaws.com/kmweb />
		ds.setUrl("jdbc:mysql://localhost/egito");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("");
		log.info("Datasource configurado: " + ds.toString());
		return ds;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		log.info("Configurando [entityManagerFactory]...");
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "pandox.china" });
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.put("hibernate.format_sql", "false");
		hibernateProperties.put("hibernate.show_sql", "false");

		entityManagerFactoryBean.setJpaProperties(hibernateProperties);

		log.info(entityManagerFactoryBean);
		return entityManagerFactoryBean;
	}

	// ======================================================================
	// [ SessionFactory config ]
	// ======================================================================
	// @Bean
	// public LocalSessionFactoryBean sessionFactory() {
	// log.info("Configurando [sessionFactory]...");
	// Properties hibernateProperties = new Properties();
	// hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
	// hibernateProperties.put("hibernate.dialect",
	// "org.hibernate.dialect.MySQLDialect");
	// hibernateProperties.put("format_sql", "true");
	// hibernateProperties.put("showSql", "true");
	//
	// LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	// sessionFactory.setDataSource(dataSource());
	// sessionFactory.setPackagesToScan(new String[] { "pandox.egito" });
	// sessionFactory.setHibernateProperties(hibernateProperties);
	//
	// log.info(sessionFactory);
	// return sessionFactory;
	// }
	// @Bean
	// public HibernateTransactionManager transactionManager() {
	// log.info("Configurando [transactionManager]...");
	// log.info(sessionFactory());
	// return new HibernateTransactionManager(sessionFactory().getObject());
	// }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean(name = "resourceBundleMessageSource")
	public static final ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:Messages");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}

	@Bean
	public static final ReloadableResourceBundleMessageSource config() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:config");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}

	// ======================================================================
	// [ Velocity BEANs Configurer and View Resover ]
	// ======================================================================
	@Bean
	public static final VelocityConfigurer velocityConfig() {
		String path = "/pages";
		log.info("Configurando Velocity...");
		log.info("Diretorio dos templates:" + path);

		velocityProperties.put("input.encoding", "UTF-8");
		velocityProperties.put("output.encoding", "UTF-8");
		velocityProperties.put("directive.foreach.counter.name", "velocityCount");
		velocityProperties.put("directive.foreach.counter.initial.value ", "1");
		VelocityConfigurer config = new VelocityConfigurer();
		config.setVelocityPropertiesMap(velocityProperties);
		config.setResourceLoaderPath(path);
		return config;
	}

	@Bean
	public static final VelocityViewResolver viewResolver() {
		VelocityViewResolver viewResolver = new VelocityViewResolver();
		viewResolver.setToolboxConfigLocation("/WEB-INF/toolbox.xml");
		HashMap<String, Object> velocityProperties = new HashMap<String, Object>();
		viewResolver.setCache(true);
		viewResolver.setSuffix(".vm");
		viewResolver.setAttributesMap(velocityProperties);
		viewResolver.setExposeSpringMacroHelpers(true);
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
