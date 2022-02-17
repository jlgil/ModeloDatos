package com.personal.diccionariobd.modelodatos.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.personal.diccionariobd.modelodatos.dao.dto.CiuuDto;

public class RowMapperCiuu implements RowMapper{

	@Override
	public CiuuDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CiuuDto ciuu = new CiuuDto();
		
		ciuu.setCodigo(rs.getString("codigo"));
		ciuu.setDescripcion(rs.getString("descripcion"));
		return ciuu;
	}

}
