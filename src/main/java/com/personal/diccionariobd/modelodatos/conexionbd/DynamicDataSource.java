package com.personal.diccionariobd.modelodatos.conexionbd;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
 
 
public class DynamicDataSource extends AbstractRoutingDataSource {
    private boolean debug = true;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Object, Object> dynamicTargetDataSources;
    private Object dynamicDefaultTargetDataSource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
 
    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DBContextHolder.getDataSource();
        if (!StringUtils.isEmpty(datasource)) {
            Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
            if (dynamicTargetDataSources2.containsKey(datasource)) {
                log.info ("--- Fuente de datos actual:" + datasource + "---");
            } else {
                log.info ("Fuente de datos que no existe:");
                return null;
 // lanzar nueva ADIException ("Fuente de datos que no existe:" + fuente de datos, 500);
            }
        } else {
           log.info ("--- Fuente de datos actual: Fuente de datos predeterminada ---");
        }
 
        return datasource;
    }
 
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
 
        super.setTargetDataSources(targetDataSources);
 
        this.dynamicTargetDataSources = targetDataSources;
 
    }
 
 
         // Crear fuente de datos
    public boolean createDataSource(String key, String driveClass, String url, String username, String password, String databasetype) {
        try {
                try {// Elimina el error de falla de conexión
                Class.forName(driveClass);
                DriverManager.getConnection (url, username, password); // Equivalente a conectarse a la base de datos
 
            } catch (Exception e) {
 
                return false;
            }
            @SuppressWarnings("resource")
//            HikariDataSource druidDataSource = new HikariDataSource();
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setDriverClassName(driveClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
                         druidDataSource.setInitialSize (2); // Número de conexiones físicas establecidas durante la inicialización. La inicialización ocurre cuando se llama al método init, o la primera vez que se obtiene la conexión.
                         druidDataSource.setMaxActive (20); // Número máximo de grupos de conexiones
                         druidDataSource.setMaxWait (60000); // Tiempo máximo de espera al adquirir conexión, en milisegundos. Cuando el número de enlaces ha alcanzado el número máximo de enlaces, la aplicación esperará si aún necesita obtener el enlace, esperando que se libere el enlace y regresando al grupo de enlaces, si el tiempo de espera es demasiado largo, la espera Debe iniciarse, de lo contrario, la aplicación es muy posible fenómeno de avalancha
                         druidDataSource.setMinIdle (1); // Número mínimo de grupos de conexiones
            String validationQuery = "select now()";
//            if("mysql".equalsIgnoreCase(databasetype)) {
//                driveClass = DBUtil.mysqldriver;
//                validationQuery = "select 1";
//            } else if("oracle".equalsIgnoreCase(databasetype)){
//                driveClass = DBUtil.oracledriver;
 // druidDataSource.setPoolPreparedStatements (true); // Si se almacenará en caché readyStatement, que es PSCache. PSCache mejora enormemente el rendimiento de las bases de datos que admiten cursores, como Oracle. Se recomienda cerrarlo en mysql.
//                druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(50);
//                int sqlQueryTimeout = ADIPropUtil.sqlQueryTimeOut();
 // druidDataSource.setConnectionProperties ("oracle.net.CONNECT_TIMEOUT = 6000; oracle.jdbc.ReadTimeout =" + sqlQueryTimeout); // Para consultas SQL que requieren mucho tiempo, estará limitado por el control de ReadTimeout, en milisegundos
//            } else if("sqlserver2000".equalsIgnoreCase(databasetype)){
//                driveClass = DBUtil.sql2000driver;
//                validationQuery = "select 1";
//            } else if("sqlserver".equalsIgnoreCase(databasetype)){
//                driveClass = DBUtil.sql2005driver;
//                validationQuery = "select 1";
//            }
                         druidDataSource.setTestOnBorrow (true); // Ejecute validationQuery al solicitar una conexión para comprobar si la conexión es válida. Se recomienda configurarlo en TRUE para evitar que la conexión no esté disponible
                         druidDataSource.setTestWhileIdle (true); // Se recomienda configurar a true, lo que no afectará el rendimiento y garantizará la seguridad. Verifique cuando solicite una conexión. Si el tiempo de inactividad es mayor que timeBetweenEvictionRunsMillis, ejecute una validationQuery para verificar si la conexión es válida.
            druidDataSource.setValidationQuery (validationQuery); // SQL utilizado para verificar si la conexión es válida, el requisito es una declaración de consulta. Si validationQuery es nulo, testOnBorrow, testOnReturn, testWhileIdle no funcionará.
                         druidDataSource.setFilters ("stat"); // El tipo de atributo es una cadena. Los complementos de extensión se configuran mediante alias. Los complementos más utilizados son: filter: stat para monitorear estadísticas y filter: log4j para defenderse de la inyección SQL filtro: pared
                         druidDataSource.setTimeBetweenEvictionRunsMillis (60000); // Configure cuánto tiempo es el intervalo para realizar una verificación, verifique las conexiones inactivas que deben cerrarse, la unidad es milisegundos
                         druidDataSource.setMinEvictableIdleTimeMillis (60000); // Configura el tiempo mínimo de supervivencia de una conexión en el pool, la unidad es milisegundos, aquí se configura como 3 minutos 180000
                         //druidDataSource.setKeepAlive (true); // Después de abrir druid.keepAlive, cuando el grupo de conexiones está inactivo, las conexiones dentro del número de minIdle en el grupo y el tiempo de inactividad supera minEvictableIdleTimeMillis ejecutará la operación keepAlive, es decir, ejecutará la consulta SQL especificado por druid.validationQuery, generalmente seleccione * de dual, siempre que minEvictableIdleTimeMillis esté configurado para ser menor que el tiempo de corte del firewall, puede garantizar que la detección de mantenimiento se realice automáticamente cuando la conexión esté inactiva y no ser cortado por el cortafuegos
                         druidDataSource.setRemoveAbandoned (true); // Si eliminar la conexión filtrada / recuperar si se excede el límite de tiempo.
                         druidDataSource.setRemoveAbandonedTimeout (3600); // El tiempo definido de la conexión filtrada (para exceder el tiempo de procesamiento de la transacción máxima); la unidad son segundos. Aquí está configurado como 1 hora
                         druidDataSource.setLogAbandoned (true); //// Eliminar si se registra el registro cuando se produce la conexión filtrada
            druidDataSource.init();
            this.dynamicTargetDataSources.put(key, druidDataSource);
                         setTargetDataSources (this.dynamicTargetDataSources); // Asignar mapa a TargetDataSources de la clase padre
                         super.afterPropertiesSet (); // Ponga la información de conexión en TargetDataSources en la gestión de resolveDataSources
                         log.info  ("La inicialización de la fuente de datos se realizó correctamente");
                         //log.info(key+"Summary of data source: "+ druidDataSource.dump ());
            log.info("Metodo createDataSource con parametros Antes de Establer JDBCTemplate: " + jdbcTemplate);
            jdbcTemplate.setDataSource(druidDataSource);
            log.info("Metodo createDataSource con parametros despues de Establer JDBCTemplate: " + jdbcTemplate);
            return true;
        } catch (Exception e) {
            log.error(e + "");
            return false;
        }
    }
         // Eliminar fuente de datos
    public boolean delDatasources(String datasourceid) {
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(datasourceid)) {
            Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();

            for (DruidDataSource l : druidDataSourceInstances) {
                if (datasourceid.equals(l.getName())) {
  
                	
                    dynamicTargetDataSources2.remove(datasourceid);
                   // DruidDataSourceStatManager.removeDataSource(l);
  
                    l.close();
                    
                  	
               
                    

                    
                    setTargetDataSources (dynamicTargetDataSources2); // Asignar mapa a TargetDataSources de la clase principal
                    super.afterPropertiesSet (); // Ponga la información de conexión en TargetDataSources en la gestión de resolveDataSources
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
 
         // Prueba si la conexión de la fuente de datos es válida
    public boolean testDatasource(String key, String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
    }
 
    /**
     * @param debug
     *            the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
 
    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }
 
    /**
     * @return the dynamicTargetDataSources
     */
    public Map<Object, Object> getDynamicTargetDataSources() {
        return dynamicTargetDataSources;
    }
 
    /**
     * @param dynamicTargetDataSources
     *            the dynamicTargetDataSources to set
     */
    public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
        this.dynamicTargetDataSources = dynamicTargetDataSources;
    }
 
    /**
     * @return the dynamicDefaultTargetDataSource
     */
    public Object getDynamicDefaultTargetDataSource() {
        return dynamicDefaultTargetDataSource;
    }
 
    /**
     * @param dynamicDefaultTargetDataSource
     *            the dynamicDefaultTargetDataSource to set
     */
    public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
        this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
    }
 
    public void createDataSourceWithCheck(DataSources dataSource) throws Throwable {
        String datasourceId = dataSource.getIdDatasource();
        log.info ("Comprobando la fuente de datos:" + datasourceId);
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(datasourceId)) {
            log.info ("Fuente de datos" + datasourceId + "se ha creado antes, listo para probar si la fuente de datos es normal ...");
            //DataSource druidDataSource = (DataSource) dynamicTargetDataSources2.get(datasourceId);
            DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources2.get(datasourceId);
            boolean rightFlag = true;
            Connection connection = null;
            try {
                log.info (datasourceId + "Resumen de la fuente de datos -> conexiones inactivas actuales:" + druidDataSource.getPoolingCount ());
                long activeCount = druidDataSource.getActiveCount();
                log.info (datasourceId + "Resumen de la fuente de datos-> Número de conexión activa actual:" + activeCount);
                if(activeCount > 0) {
                   log.info (datasourceId + "Resumen de la fuente de datos -> información de la pila de conexión activa:" + druidDataSource.getActiveConnectionStackTrace ());
                }
                log.info ("Listo para conectarse a la base de datos ...");
                connection = druidDataSource.getConnection();
                log.info ("fuente de datos" + datasourceId + "normal");
                log.info ("Estableciendo el DataSource en el JDBCTemplate" + jdbcTemplate);
                jdbcTemplate.setDataSource(druidDataSource);
                log.info ("Establecido el DataSource en el JDBCTemplate" + jdbcTemplate);
                
            } catch (Exception e) {
                                 log.error (e.getMessage (), e); // Imprime la información de la excepción en el archivo de registro
                rightFlag = false;
                                 log.info ("La fuente de datos en caché" + datasourceId + "ha sido invalidada, lista para borrar ...");
                if(delDatasources(datasourceId)) {
                    log.info ("Fuente de datos en caché eliminada con éxito");
                } else {
                    log.info ("Error al eliminar la fuente de datos en caché");
                }
            } finally {
                if(null != connection) {
                    connection.close();
                }
            }
            if(rightFlag) {
                log.info ("No es necesario volver a crear la fuente de datos");
                return;
            } else {
               log.info ("Listo para recrear la fuente de datos ...");
                createDataSource(dataSource);
                log.info ("Volver a crear la fuente de datos completa");
            }
        } else {
            createDataSource(dataSource);
        }
 
    }
 
    private  void createDataSource(DataSources dataSource) throws Throwable {
        String datasourceId = dataSource.getIdDatasource();
        log.info ("Listo para crear una fuente de datos" + datasourceId);
        String databasetype = dataSource.getTipoDataSources();
        String username = dataSource.getUserName();
        String password = dataSource.getPass();
        String url = dataSource.getUrl();
        String driveClass = "org.postgresql.Driver"; //"com.mysql.cj.jdbc.Driver";
//        if("mysql".equalsIgnoreCase(databasetype)) {
//            driveClass = DBUtil.mysqldriver;
//        } else if("oracle".equalsIgnoreCase(databasetype)){
//            driveClass = DBUtil.oracledriver;
//        }  else if("sqlserver2000".equalsIgnoreCase(databasetype)){
//            driveClass = DBUtil.sql2000driver;
//        } else if("sqlserver".equalsIgnoreCase(databasetype)){
//            driveClass = DBUtil.sql2005driver;
//        }
        if(testDatasource(datasourceId,driveClass,url,username,password)) {
            boolean result = this.createDataSource(datasourceId, driveClass, url, username, password, databasetype);
            if(!result) {
               log.error ("Fuente de datos" + datasourceId + "La configuración es correcta, pero la creación falló");
 // lanzar nueva ADIException ("Fuente de datos" + datasourceId + "La configuración es correcta, pero la creación falló", 500);
            }
        } else {
                log.error ("Error de configuración de la fuente de datos");
                throw new Throwable("Error en la configuracion de datos");
 // lanzar nueva ADIException ("Error de configuración de la fuente de datos", 500);
        }
    }
    
    
 
}
 