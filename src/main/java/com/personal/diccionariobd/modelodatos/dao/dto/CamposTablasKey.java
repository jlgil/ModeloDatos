package com.personal.diccionariobd.modelodatos.dao.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class CamposTablasKey implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Column(name="id_tabla")
	private int codTabla;
	
	@Column(name="id_campo")
	private int idCampo;
	
	public CamposTablasKey(int codTabla, int idCampo) {
		super();
		this.codTabla = codTabla;
		this.idCampo = idCampo;
	}
	
	
	public CamposTablasKey() {

	}


	public int getCodTabla() {
		return codTabla;
	}
	public void setCodTabla(int codTabla) {
		this.codTabla = codTabla;
	}
	public int getIdCampo() {
		return idCampo;
	}
	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	@Override
	public String toString() {
		return "CamposTablasKey [codTabla=" + codTabla + ", idCampo=" + idCampo
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codTabla;
		result = prime * result + idCampo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CamposTablasKey))
			return false;
		CamposTablasKey other = (CamposTablasKey) obj;
		if (codTabla != other.codTabla)
			return false;
		if (idCampo != other.idCampo)
			return false;
		return true;
	}
	
}
