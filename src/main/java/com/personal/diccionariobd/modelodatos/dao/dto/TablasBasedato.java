package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tablas_basedatos database table.
 * 
 */
@Entity
@Table(name="tablas_basedatos")
@NamedQuery(name="TablasBasedato.findAll", query="SELECT t FROM TablasBasedato t")
public class TablasBasedato implements Serializable {
	private static final long serialVersionUID = 1L;

/*	@Column(name="id_bd")
	private Integer idBd;

	@Column(name="id_datasource")
	private Integer idDatasource;

	@Column(name="id_tabla")
	private Integer idTabla;*/
	
	@EmbeddedId
	private TablasBaseDatosKey tablasBaseDatosKey;

	@Column(name="data_sensible")
	private String dataSensible;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Column(name="frecuencia_respaldo")
	private String frecuenciaRespaldo;


	@Column(name="nombre_tabla")
	private String nombreTabla;

	@Column(name="tipo_particion")
	private String tipoParticion;

	@Column(name="tipo_tabla")
	private String tipoTabla;
	
	@Transient
	private List<CamposTablasBaseDato> campoTablasEsquema;
	
	@Transient
	private String nombreBaseDatos;

	public TablasBasedato() {
	}

	public String getDataSensible() {
		return this.dataSensible;
	}

	public void setDataSensible(String dataSensible) {
		this.dataSensible = dataSensible;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFrecuenciaRespaldo() {
		return this.frecuenciaRespaldo;
	}

	public void setFrecuenciaRespaldo(String frecuenciaRespaldo) {
		this.frecuenciaRespaldo = frecuenciaRespaldo;
	}


	public String getNombreTabla() {
		return this.nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public String getTipoParticion() {
		return this.tipoParticion;
	}

	public void setTipoParticion(String tipoParticion) {
		this.tipoParticion = tipoParticion;
	}

	public String getTipoTabla() {
		return this.tipoTabla;
	}

	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}

	public TablasBaseDatosKey getTablasBaseDatosKey() {
		return tablasBaseDatosKey;
	}

	public void setTablasBaseDatosKey(TablasBaseDatosKey tablasBaseDatosKey) {
		this.tablasBaseDatosKey = tablasBaseDatosKey;
	}

	public List<CamposTablasBaseDato> getCampoTablasEsquema() {
		return campoTablasEsquema;
	}

	public void setCampoTablasEsquema(List<CamposTablasBaseDato> campoTablasEsquema) {
		this.campoTablasEsquema = campoTablasEsquema;
	}

	public String getNombreBaseDatos() {
		return nombreBaseDatos;
	}

	public void setNombreBaseDatos(String nombreBaseDatos) {
		this.nombreBaseDatos = nombreBaseDatos;
	}

}