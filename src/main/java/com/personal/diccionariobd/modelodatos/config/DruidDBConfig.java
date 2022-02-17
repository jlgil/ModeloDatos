package com.personal.diccionariobd.modelodatos.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alibaba.druid.pool.DruidDataSource;
import com.personal.diccionariobd.modelodatos.conexionbd.DynamicDataSource;


@Configuration
public class DruidDBConfig {

	private static final Logger log = LoggerFactory.getLogger(DruidDBConfig.class);
	
    // información de conexión de la base de datos adi
@Value("${spring.datasource.url}")
private String dbUrl;
@Value("${spring.datasource.username}")
private String username;
@Value("${spring.datasource.password}")
private String password;
@Value("${spring.datasource.hikari.driver-class-name}")
private String driverClassName;
/*
// Información de conexión del grupo de conexiones
@Value("${spring.datasource.initialSize}")
*/
private int initialSize;
@Value("${spring.datasource.minIdle}")
private int minIdle;
@Value("${spring.datasource.maxActive}")
private int maxActive;
@Value("${spring.datasource.maxWait}")
private int maxWait;

@Bean // Declararlo como una instancia de Bean
@Primary // En el mismo DataSource, primero use el DataSource etiquetado
@Qualifier("mainDataSource")
public DataSource dataSource() throws SQLException {
   DruidDataSource datasource = new DruidDataSource();
            // Información básica de conexión
   datasource.setUrl(this.dbUrl);
   datasource.setUsername(username);
   datasource.setPassword(password);
   datasource.setDriverClassName(driverClassName);
            // Información de conexión del grupo de conexiones
   datasource.setInitialSize(5);
   datasource.setMinIdle(minIdle);
   datasource.setMaxActive(maxActive);
   datasource.setMaxWait(maxWait);
            datasource.setPoolPreparedStatements (true); // Si se debe almacenar en caché readyStatement, que es PSCache. PSCache mejora enormemente el rendimiento de las bases de datos que admiten cursores, como Oracle. Se recomienda cerrarlo en mysql.
   datasource.setMaxPoolPreparedStatementPerConnectionSize(50);
            // datasource.setConnectionProperties ("oracle.net.CONNECT_TIMEOUT = 6000; oracle.jdbc.ReadTimeout = 60000"); // Para la consulta sql que consume mucho tiempo, estará limitada por el control de ReadTimeout, en milisegundos
            datasource.setConnectionProperties ("druid.stat.mergeSql = true; druid.stat.slowSqlMillis = 5000"); // Para la consulta SQL que consume mucho tiempo, estará limitada por el control de ReadTimeout, en milisegundos
            datasource.setTestOnBorrow (true); // Ejecutar validationQuery para comprobar si la conexión es válida al solicitar una conexión. Se recomienda configurarlo en TRUE para evitar que la conexión obtenida no esté disponible
            datasource.setTestWhileIdle (true); // Se recomienda configurar en true, lo que no afectará el rendimiento y garantizará la seguridad. Verifique cuando solicite una conexión. Si el tiempo de inactividad es mayor que timeBetweenEvictionRunsMillis, ejecute una validationQuery para verificar si la conexión es válida.
   String validationQuery = "select NOW ()";
            datasource.setValidationQuery (validationQuery); // SQL utilizado para detectar si la conexión es válida, el requisito es una declaración de consulta. Si validationQuery es nulo, testOnBorrow, testOnReturn, testWhileIdle no funcionará.
            datasource.setFilters ("stat, wall"); // El tipo de atributo es una cadena. Los complementos de extensión se configuran mediante alias. Los complementos más utilizados son: filtro para estadísticas de monitoreo: filtro para registros de estadísticas: filtro para log4j para defenderse de la inyección SQL: wall
   datasource.setTimeBetweenEvictionRunsMillis (60000); // Configure cuánto tiempo es el intervalo para realizar una verificación, verifique las conexiones inactivas que deben cerrarse, la unidad es milisegundos
            datasource.setMinEvictableIdleTimeMillis (180000); // Configura el tiempo mínimo de supervivencia de una conexión en el pool, la unidad es milisegundos, aquí se configura como 3 minutos 180000
            datasource.setKeepAlive (true); // Después de abrir druid.keepAlive, cuando el grupo de conexiones está inactivo, las conexiones dentro del número de minIdle en el grupo y el tiempo de inactividad excede minEvictableIdleTimeMillis ejecutará la operación keepAlive, es decir, ejecutará la consulta SQL especificado por druid.validationQuery, generalmente seleccione * de dual, siempre que minEvictableIdleTimeMillis esté configurado para ser menor que el tiempo de corte del firewall, puede garantizar que la detección de mantenimiento se realice automáticamente cuando la conexión esté inactiva y no ser cortado por el cortafuegos
            datasource.setRemoveAbandoned (true); // Si eliminar la conexión filtrada / recuperar si se excede el límite de tiempo.
            datasource.setRemoveAbandonedTimeout (3600); // El tiempo definido de la conexión filtrada (para exceder el tiempo máximo de procesamiento de transacciones); la unidad es segundos. Aquí está configurado como 1 hora
            datasource.setLogAbandoned (true); //// Eliminar si se registra la conexión filtrada
   return datasource;


}




@Bean(name = "dynamicDataSource")
@Qualifier("dynamicDataSource")
public DynamicDataSource dynamicDataSource() throws SQLException {
   DynamicDataSource dynamicDataSource = new DynamicDataSource();
   dynamicDataSource.setDebug(false);
            // Configurar la fuente de datos predeterminada
            // Configuración predeterminada de la fuente de datos DefaultTargetDataSource
   dynamicDataSource.setDefaultTargetDataSource(dataSource());
   Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
            // Configuración de fuente de datos adicional TargetDataSources
   targetDataSources.put("mainDataSource", dataSource());
   dynamicDataSource.setTargetDataSources(targetDataSources);
   return dynamicDataSource;
}


@Bean
public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	 
	 //System.out.println("Datasource des jdc" + dataSource.toString());
	 log.info("LogDebug: Driver Utilizado:" + dataSource.toString());
	 
	 

     return new JdbcTemplate(dataSource);
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
