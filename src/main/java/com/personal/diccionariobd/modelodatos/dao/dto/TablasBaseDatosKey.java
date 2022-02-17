package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TablasBaseDatosKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="id_bd")
	private Integer idBd;

	@Column(name="id_datasource")
	private String idDatasource;

	@Column(name="id_tabla")
	private Integer idTabla;
	
	

	public TablasBaseDatosKey() {

	}



	public TablasBaseDatosKey(Integer idBd, String idDatasource, Integer idTabla) {
		super();
		this.idBd = idBd;
		this.idDatasource = idDatasource;
		this.idTabla = idTabla;
	}



	@Override
	public String toString() {
		return "TablasBaseDatosKey [idBd=" + idBd + ", idDatasource=" + idDatasource + ", idTabla=" + idTabla + "]";
	}



	public Integer getIdBd() {
		return idBd;
	}



	public void setIdBd(Integer idBd) {
		this.idBd = idBd;
	}



	public String getIdDatasource() {
		return idDatasource;
	}



	public void setIdDatasource(String idDatasource) {
		this.idDatasource = idDatasource;
	}



	public Integer getIdTabla() {
		return idTabla;
	}



	public void setIdTabla(Integer idTabla) {
		this.idTabla = idTabla;
	}

}
