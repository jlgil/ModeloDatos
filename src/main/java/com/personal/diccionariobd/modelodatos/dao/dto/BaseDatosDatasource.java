package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.*;




/**
 * The persistent class for the base_datos_datasource database table.
 * 
 */
@Entity
@Table(name="base_datos_datasource")
public class BaseDatosDatasource implements Serializable {
	private static final long serialVersionUID = 1L;


	@EmbeddedId
	private BaseDatosDatasourceKey id;
	

	@Column(name="nombre_bd")
	private String nombreBd;

	public BaseDatosDatasource() {
	}

	public BaseDatosDatasourceKey getId() {
		return id;
	}

	public void setId(BaseDatosDatasourceKey id) {
		this.id = id;
	}

	public String getNombreBd() {
		return nombreBd;
	}

	public void setNombreBd(String nombreBd) {
		this.nombreBd = nombreBd;
	}





}