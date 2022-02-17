package com.personal.diccionariobd.modelodatos.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;

public class RowMapperDatabase  implements RowMapper{

	@Override
	public DataSources mapRow(ResultSet rs, int rowNum) throws SQLException {
		DataSources baseDatos = new DataSources();
		baseDatos.setBdInicial(rs.getString("bd_inicial"));
		baseDatos.setIdDatasource(rs.getString("id_datasource"));
		baseDatos.setNombreDataSource(rs.getString("nombre_datasource"));
		baseDatos.setPass(rs.getString("pass"));
		baseDatos.setTipoDataSources(rs.getString("tipo_datasource"));
		baseDatos.setUrl(rs.getString("url"));
		baseDatos.setUserName(rs.getString("user_name"));
		return baseDatos;
	}
/*
 * 
 * id_datasource     varchar(255),
nombre_datasource varchar(255) NOT NULL,
tipo_datasource   varchar(255) NOT NULL,
url           varchar(255),
user_name     varchar(255),
pass          varchar(255), 
bd_inicial    varchar(255)
 */
}
