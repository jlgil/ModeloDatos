package com.personal.diccionariobd.modelodatos.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="datasources")
public class DataSources {
/*
 *
create table datasources(
id_datasource     serial NOT NULL,
nombre_datasource varchar(255) NOT NULL,
tipo_datasource   varchar(255) NOT NULL,
url           varchar(255),
user_name     varchar(255),
pass          varchar(255), 
bd_inicial    varchar(255)
)  ;
 * 
 * */
 
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_datasource")
	@NotNull(message="Id de Datasources no puede ser nulo")
	private String idDatasource;
	
	@Column(name="nombre_datasource")
	@NotNull(message="Nombre de datasource no puede ser nulo")
	@NotBlank(message="Nombre de datasource no puede estar en blanco")
	private String nombreDataSource;
	
	@Column(name="tipo_datasource")
	@NotNull(message="Tipo de Datasources no puede ser nulo")
	@NotBlank(message="Tipo de Datasources no puede estar en blanco")
	private String tipoDataSources;
	
	@Column(name="url")
	@NotNull(message="Url no puede ser nulo")
	@NotBlank(message="Url no puede estar en blanco")
	private String url;
	
	@Column(name="user_name")
	@NotNull(message="Usuario no puede ser nulo")
	@NotBlank(message="Usuario no puede estar en blanco")
	private String userName;
	
	@Column(name="pass")
	@NotNull(message="Password no puede ser nulo")
	@NotBlank(message="Password no puede estar en blanco")
	private String pass;
	
	@Column(name="bd_inicial")
	@NotNull(message="Base de Datos Inicial no puede ser nulo")
	@NotBlank(message="Base de Datos Inicial no puede estar en blanco")
	private String bdInicial;
	

	@Override
	public String toString() {
		return "DataSource [idDatasource=" + idDatasource
				+ ", nombreDataSource=" + nombreDataSource
				+ ", tipoDataSources=" + tipoDataSources + ", url=" + url
				+ ", userName=" + userName + ", pass=" + pass + ", bdInicial="
				+ bdInicial + "]";
	}
	public String getIdDatasource() {
		return idDatasource;
	}
	public void setIdDatasource(String idDatasource) {
		this.idDatasource = idDatasource;
	}
	public String getNombreDataSource() {
		return nombreDataSource;
	}
	public void setNombreDataSource(String nombreDataSource) {
		this.nombreDataSource = nombreDataSource;
	}
	public String getTipoDataSources() {
		return tipoDataSources;
	}
	public void setTipoDataSources(String tipoDataSources) {
		this.tipoDataSources = tipoDataSources;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getBdInicial() {
		return bdInicial;
	}
	public void setBdInicial(String bdInicial) {
		this.bdInicial = bdInicial;
	}
	
	
}
