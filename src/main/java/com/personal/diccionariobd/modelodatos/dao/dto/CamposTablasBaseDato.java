package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the campos_tablas_base_datos database table.
 * 
 */
@Entity
@Table(name="campos_tablas_base_datos")
@NamedQuery(name="CamposTablasBaseDato.findAll", query="SELECT c FROM CamposTablasBaseDato c")
public class CamposTablasBaseDato implements Serializable {
	private static final long serialVersionUID = 1L;

/*	
	@Column(name="id_bd")
	private Integer idBd;

	@Column(name="id_campo")
	private Integer idCampo;

	@Column(name="id_datasource")
	private Integer idDatasource;

	@Column(name="id_tabla")
	private Integer idTabla;*/
	
	@EmbeddedId
	private CamposTablasBaseDatoKey camposTablasBaseDatoKey;
	
	@Column(name="campo_indice")
	private String campoIndice;

	@Column(name="descripcion_campo")
	private String descripcionCampo;



	@Column(name="longitud_campo")
	private Integer longitudCampo;

	@Column(name="nombre_campo")
	private String nombreCampo;

	@Column(name="permite_nulo")
	private String permiteNulo;

	@Column(name="tipo_dato")
	private String tipoDato;

	public CamposTablasBaseDato() {
	}

	public String getCampoIndice() {
		return this.campoIndice;
	}

	public void setCampoIndice(String campoIndice) {
		this.campoIndice = campoIndice;
	}

	public String getDescripcionCampo() {
		return this.descripcionCampo;
	}

	public void setDescripcionCampo(String descripcionCampo) {
		this.descripcionCampo = descripcionCampo;
	}


	public Integer getLongitudCampo() {
		return this.longitudCampo;
	}

	public void setLongitudCampo(Integer longitudCampo) {
		this.longitudCampo = longitudCampo;
	}

	public String getNombreCampo() {
		return this.nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public String getPermiteNulo() {
		return this.permiteNulo;
	}

	public void setPermiteNulo(String permiteNulo) {
		this.permiteNulo = permiteNulo;
	}

	public String getTipoDato() {
		return this.tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public CamposTablasBaseDatoKey getCamposTablasBaseDatoKey() {
		return camposTablasBaseDatoKey;
	}

	public void setCamposTablasBaseDatoKey(CamposTablasBaseDatoKey camposTablasBaseDatoKey) {
		this.camposTablasBaseDatoKey = camposTablasBaseDatoKey;
	}

}