package com.personal.diccionariobd.modelodatos.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.personal.diccionariobd.modelodatos.dao.dto.TablasBaseDatosKey;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;
import com.personal.diccionariobd.modelodatos.dao.impl.CargarDatosEsquemaDAOImpl;

public class RowMapperTablasEsquemas implements RowMapper{
	private static final Logger log = LoggerFactory.getLogger(RowMapperTablasEsquemas.class);
	
	private String idDataSources;
	private int idBaseDatos;
	
	public RowMapperTablasEsquemas(String idDataSources, int idBaseDatos) {
		super();
		this.idDataSources = idDataSources;
		this.idBaseDatos = idBaseDatos;
	}

	@Override
	public TablasBasedato mapRow(ResultSet rs, int rowNum) throws SQLException {
		int secuencial = 0;
		

		secuencial = rowNum + 1;
		
		log.info("Desde MapRow numero de linea: " + rowNum);
		log.info("Desde MapRow secuencial: " + secuencial);
		
		
		TablasBasedato tablas = new TablasBasedato();
		tablas.setDataSensible(rs.getString("datasensible"));
		tablas.setDescripcion(rs.getString("descripcion"));
		tablas.setFechaCreacion(rs.getDate("fechacreacion"));
		tablas.setFrecuenciaRespaldo(rs.getString("frecuenciarespaldo"));
		tablas.setNombreTabla(rs.getString("nombretabla"));
		tablas.setTipoParticion(rs.getString("tipoparticion"));
		tablas.setTipoTabla(rs.getString("tipotabla"));
		tablas.setTablasBaseDatosKey(new TablasBaseDatosKey(this.idBaseDatos, this.idDataSources, secuencial));
		tablas.setNombreBaseDatos(rs.getString("nombrebasedatos"));
		return tablas;
	}

	public String getIdDataSources() {
		return idDataSources;
	}

	public void setIdDataSources(String idDataSources) {
		this.idDataSources = idDataSources;
	}

	public int getIdBaseDatos() {
		return idBaseDatos;
	}

	public void setIdBaseDatos(int idBaseDatos) {
		this.idBaseDatos = idBaseDatos;
	}

}
