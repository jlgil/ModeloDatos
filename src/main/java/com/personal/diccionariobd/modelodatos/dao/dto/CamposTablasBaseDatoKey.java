package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CamposTablasBaseDatoKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="id_bd")
	private Integer idBd;

	@Column(name="id_campo")
	private Integer idCampo;

	@Column(name="id_datasource")
	private String idDatasource;

	@Column(name="id_tabla")
	private Integer idTabla;
	
	

	public CamposTablasBaseDatoKey(Integer idBd, Integer idCampo, String idDatasource, Integer idTabla) {
		super();
		this.idBd = idBd;
		this.idCampo = idCampo;
		this.idDatasource = idDatasource;
		this.idTabla = idTabla;
	}
	
	

	public CamposTablasBaseDatoKey() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getIdBd() {
		return idBd;
	}

	public void setIdBd(Integer idBd) {
		this.idBd = idBd;
	}

	public Integer getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(Integer idCampo) {
		this.idCampo = idCampo;
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

	@Override
	public String toString() {
		return "CamposTablasBaseDatoKey [idBd=" + idBd + ", idCampo=" + idCampo + ", idDatasource=" + idDatasource
				+ ", idTabla=" + idTabla + "]";
	}

}
