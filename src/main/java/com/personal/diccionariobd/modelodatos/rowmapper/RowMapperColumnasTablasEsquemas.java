package com.personal.diccionariobd.modelodatos.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDato;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDatoKey;

public class RowMapperColumnasTablasEsquemas implements RowMapper {
	
	private String idDataSources;
	private int idBD;
	private int idTabla;
	
	
	

	public RowMapperColumnasTablasEsquemas(String idDataSources, int idBD,
			int idTabla) {
		super();
		this.idDataSources = idDataSources;
		this.idBD = idBD;
		this.idTabla = idTabla;
	}


	@Override
	public CamposTablasBaseDato mapRow(ResultSet rs, int rowNum) throws SQLException {
		CamposTablasBaseDato campos = new CamposTablasBaseDato();
		
		int idCampo = rs.getInt("id_campo");
		
				
		campos.setCampoIndice(rs.getString("campo_indice"));
		campos.setDescripcionCampo(rs.getString("descripcion_campo"));
		campos.setLongitudCampo(rs.getInt("longitud_campo"));
		campos.setNombreCampo(rs.getString("nombre_campo"));
		campos.setPermiteNulo(rs.getString("permite_nulo"));
		campos.setTipoDato(rs.getString("tipo_dato"));
		campos.setCamposTablasBaseDatoKey(new CamposTablasBaseDatoKey(this.idBD, idCampo, this.idDataSources, this.idTabla));
		
		return campos;
	}


	public String getIdDataSources() {
		return idDataSources;
	}


	public void setIdDataSources(String idDataSources) {
		this.idDataSources = idDataSources;
	}


	public int getIdBD() {
		return idBD;
	}


	public void setIdBD(int idBD) {
		this.idBD = idBD;
	}


	public int getIdTabla() {
		return idTabla;
	}


	public void setIdTabla(int idTabla) {
		this.idTabla = idTabla;
	}

}
