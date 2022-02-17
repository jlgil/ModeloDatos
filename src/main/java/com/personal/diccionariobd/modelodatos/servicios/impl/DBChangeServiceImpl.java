package com.personal.diccionariobd.modelodatos.servicios.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.personal.diccionariobd.modelodatos.conexionbd.DBContextHolder;
import com.personal.diccionariobd.modelodatos.conexionbd.DynamicDataSource;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperDatabase;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperGenerico;
import com.personal.diccionariobd.modelodatos.servicios.IDBChangeService;

@Service
public class DBChangeServiceImpl implements IDBChangeService {
	private static final Logger log = LoggerFactory.getLogger(DBChangeServiceImpl.class);	
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private DynamicDataSource dynamicDataSource;
	
	@Autowired
	private DataSource bd;

	@Override
	public List<DataSources> get() {
		return template.query("select * from datasources", new RowMapperDatabase());
		//return null;
	}

	@Override
	public boolean changeDb(String datasourceId) throws Throwable {
        // Cambiar a la fuente de datos principal de forma predeterminada para buscar recursos generales
	      DBContextHolder.clearDataSource();
	      template.setDataSource(bd);
	
	      List<DataSources> dataSourcesList = get();
	
	      for (DataSources dataSource : dataSourcesList) {
	          if (dataSource.getIdDatasource().equals(datasourceId)) {
	        	  log.info("Se ha encontrado la fuente de datos que debe usarse, el datasourceId es:" + dataSource.getIdDatasource());
	              // Cree la conexión de la fuente de datos y verifique si existe, no es necesario volver a crearla
	              dynamicDataSource.createDataSourceWithCheck(dataSource);
	              // Cambiar a la fuente de datos
	              DBContextHolder.setDataSource(dataSource.getIdDatasource());
	              return true;
	          }
	      }
	      return false;
	}

	@Override
	public boolean cerrarBD(String datasourceId) throws Exception {
		return dynamicDataSource.delDatasources(datasourceId);

	}

}
