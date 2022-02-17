package com.personal.diccionariobd.modelodatos.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.util.DriverDataSource;



//@Configuration
@PropertySource(value = { "classpath:application.properties"})
//@PropertySource(value = { "file:C:/temporal/AppLogsConfig/application.properties"})
//@PropertySource(value = { "file:/home/AppLogsConfig/application.properties"})
public class AppConfig {
	
	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	
	@Autowired
    DataSource dataSource;
	

    	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		 
		 //System.out.println("Datasource des jdc" + dataSource.toString());
		 log.info("LogDebug: Driver Utilizado:" + dataSource.toString());
		 
		 

	     return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {

	       return new DataSourceTransactionManager(dataSource);
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	log.info("Creando BYCraapsss");
    	BCryptPasswordEncoder prueba = new BCryptPasswordEncoder();
    	
    	log.info("Creando BYCraapsss: " + prueba );
        //return new BCryptPasswordEncoder();
    	return prueba;
    }

}
