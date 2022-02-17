package com.personal.diccionariobd.modelodatos.conexionbd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DBContextHolder {
	private static final Logger log = LoggerFactory.getLogger(DBContextHolder.class);

       // Operación en el hilo seguro actual
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

       // Llame a este método para cambiar la fuente de datos
  public static void setDataSource(String dataSource) {
      contextHolder.set(dataSource);
               log.info ("Cambiado a la fuente de datos: {}", dataSource);
  }

       // Obtener fuente de datos
  public static String getDataSource() {
      return contextHolder.get();
  }

       // Eliminar fuente de datos
  public static void clearDataSource() {
      contextHolder.remove();
               log.info ("Cambiado a la fuente de datos principal");
  }

}
