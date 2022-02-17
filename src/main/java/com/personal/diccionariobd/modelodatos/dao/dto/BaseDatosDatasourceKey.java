package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BaseDatosDatasourceKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id_bd")
	private Integer idBd;

	@Column(name="id_datasource")
	private String idDatasource;
	
	

	public BaseDatosDatasourceKey(Integer idBd, String idDatasource) {
		super();
		this.idBd = idBd;
		this.idDatasource = idDatasource;
	}
	
	public BaseDatosDatasourceKey() {
		
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

	@Override
	public String toString() {
		return "BaseDatosDatasourceKey [idBd=" + idBd + ", idDatasource=" + idDatasource + "]";
	}

}
