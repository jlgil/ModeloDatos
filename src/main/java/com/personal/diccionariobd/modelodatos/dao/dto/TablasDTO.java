package com.personal.diccionariobd.modelodatos.dao.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.personal.diccionariobd.modelodatos.model.Tablas;


@Entity
@Table(name="tablasbd")
public class TablasDTO extends AbstracDTO<Tablas> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tabla")
	@NotNull(message="Id de Tabla no puede ser nulo")
	private int idtabla;
	
	@Column(name="nombre_basedatos")
	@NotNull(message="Nombre de Base de Datos no puede ser nulo")
	@NotBlank(message="Nombre de Base Datos no puede estar en blanco")
	private String nombreBaseDatos;
	
	@NotNull(message="Nombre de Tabla no puede ser nulo")
	@NotBlank(message="Nombre de Tabla no puede estar en blanco")
	@Column(name="nombre_tabla")
	private String nombreTabla;
	

	@Column(name="fecha_creacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaTabla;
	
	@NotNull(message="Descripcin  no puede ser nulo")
	@Size(min = 1, max = 50, message = "La descricion debe medir entre 1 y 50")
	@Column(name="descripcion")
	private String descripcion;
	
	@NotNull(message="Tipo de Tabla no puede ser nulo")
	@NotBlank(message="Tipo de Tabla no puede estar en blanco")
	@Column(name="tipoTabla")
	private String tipoTabla;
	
	@NotBlank(message="Daga Sensible Tabla no puede estar en blanco")
	@Column(name="data_sensible")
	private String dataSensible;
	
	@NotBlank(message="Frecuencia de Respaldo no puede estar en blanco")
	@Column(name="frecuencia_respaldo")
	private String frecuenciaRespaldo;
	
	@NotBlank(message="Tipo de Particion no puede estar en blanco")
	@Column(name="tipo_particion")
	private String tipoParticion;	
	
	@OneToMany(mappedBy = "tablasDTO", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CamposTablasDTO> direccionList = new ArrayList<CamposTablasDTO>();
	
	@Transient
	private CamposTablasDTO direccionNueva = new CamposTablasDTO();


	public CamposTablasDTO getDireccionNueva() {
		return direccionNueva;
	}

	public void setDireccionNueva(CamposTablasDTO direccionNueva) {
		this.direccionNueva = direccionNueva;
	}

	
	public List<CamposTablasDTO> getDireccionList() {
		return direccionList;
	}

	public void setDireccionList(List<CamposTablasDTO> direccionList) {
		this.direccionList = direccionList;
	}

	/**
	 * @return the idtabla
	 */
	public int getIdtabla() {
		return idtabla;
	}

	/**
	 * @param idtabla the idtabla to set
	 */
	public void setIdtabla(int idtabla) {
		this.idtabla = idtabla;
	}

	/**
	 * @return the nombreBaseDatos
	 */
	public String getNombreBaseDatos() {
		return nombreBaseDatos;
	}

	/**
	 * @param nombreBaseDatos the nombreBaseDatos to set
	 */
	public void setNombreBaseDatos(String nombreBaseDatos) {
		this.nombreBaseDatos = nombreBaseDatos;
	}

	/**
	 * @return the nombreTabla
	 */
	public String getNombreTabla() {
		return nombreTabla;
	}

	/**
	 * @param nombreTabla the nombreTabla to set
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * @return the fechaTabla
	 */
	public Date getFechaTabla() {
		return fechaTabla;
	}

	/**
	 * @param fechaTabla the fechaTabla to set
	 */
	public void setFechaTabla(Date fechaTabla) {
		this.fechaTabla = fechaTabla;
	}

	@Override
	public Tablas toEntity() {
		Tablas tabla = new Tablas();
		
		BeanUtils.copyProperties(this,tabla);
		
		
		return tabla;
	}
	
	@Override
	public Tablas toEntity(Tablas entity) {
		Tablas tabla = new Tablas();
		
		BeanUtils.copyProperties(entity, tabla);
		return tabla;
	}
	
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return  getIdtabla(); 
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoTabla
	 */
	public String getTipoTabla() {
		return tipoTabla;
	}

	/**
	 * @param tipoTabla the tipoTabla to set
	 */
	public void setTipoTabla(String tipoTabla) {
		this.tipoTabla = tipoTabla;
	}

	/**
	 * @return the dataSensible
	 */
	public String getDataSensible() {
		return dataSensible;
	}

	/**
	 * @param dataSensible the dataSensible to set
	 */
	public void setDataSensible(String dataSensible) {
		this.dataSensible = dataSensible;
	}

	/**
	 * @return the frecuenciaRespaldo
	 */
	public String getFrecuenciaRespaldo() {
		return frecuenciaRespaldo;
	}

	/**
	 * @param frecuenciaRespaldo the frecuenciaRespaldo to set
	 */
	public void setFrecuenciaRespaldo(String frecuenciaRespaldo) {
		this.frecuenciaRespaldo = frecuenciaRespaldo;
	}

	/**
	 * @return the tipoParticion
	 */
	public String getTipoParticion() {
		return tipoParticion;
	}

	/**
	 * @param tipoParticion the tipoParticion to set
	 */
	public void setTipoParticion(String tipoParticion) {
		this.tipoParticion = tipoParticion;
	}
	


}
