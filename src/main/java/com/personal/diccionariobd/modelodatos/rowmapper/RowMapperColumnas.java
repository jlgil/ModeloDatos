package com.personal.diccionariobd.modelodatos.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasDTO;




public class RowMapperColumnas implements RowMapper{

	@Override
	public CamposTablasDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CamposTablasDTO user = new CamposTablasDTO();
        user.setCampoIndice(rs.getString("campo_indice"));;
/*        user.setIdCampo(rs.getInt("id_campo"));
        user.setCodTabla(rs.getInt("id_tabla"));*/
        user.setLongCampo(rs.getInt("longitud_campo"));
        user.setNameCampo(rs.getString("nombre_campo"));
        user.setPermiteNulo(rs.getString("permite_nulo"));
        user.setTipoDato(rs.getString("tipo_dato"));
        user.setDescripcionCampo(rs.getString("descripcion_campo"));
        
        
        return user;
	}
	
}

	
	
