package com.personal.diccionariobd.modelodatos.dao.dto;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.personal.diccionariobd.modelodatos.model.CamposTabla;


@Entity
@Table(name="campos_tablas")
public class CamposTablasDTO extends AbstracDTO<CamposTabla> {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//id_tabla|id_columna|name_columna|tipo_columna|longitud_col|permite_nul|campo_indice
	  
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)

	


/*	@Id
	@Column(name="id_campo")
	private int idCampo;
	
	@Column(name="id_tabla")
	private int codTabla;*/
	
	@EmbeddedId
	private CamposTablasKey camposKey;
	
	@NotNull(message="Nombre del Campo no puede ser nulo")
	@NotBlank(message="Nombre del Campo no puede estar en blanco")
	@Column(name="nombre_campo")
	private String nameCampo;
	
	@Column(name="tipo_dato")
	private String tipoDato;
	
	@Column(name="longitud_campo")
	private int longCampo;
	
	@Column(name="permite_nulo")
	private String permiteNulo;
	
	@Column(name="campo_indice")
	private String campoIndice;
	
	@Column(name="descripcion_campo")
	private String descripcionCampo;
	
	@ManyToOne
	@JoinColumn(name = "id_tabla", nullable = false,insertable=false, updatable=false)
	private TablasDTO tablasDTO;
	
	
	
	
	public CamposTablasDTO() {

	}
	
	
	
	public CamposTablasDTO(int codTabla, int idCampo, String nameCampo,
			String tipoDato, int longCampo, String permiteNulo,
			String campoIndice, String descripcionCampo, TablasDTO tablasDTO,CamposTablasKey camposKey) {
		/*this.codTabla = codTabla;
		this.idCampo = idCampo;*/
		if (camposKey==null)
			camposKey = new CamposTablasKey(codTabla,idCampo);
		
		this.nameCampo = nameCampo;
		this.tipoDato = tipoDato;
		this.longCampo = longCampo;
		this.permiteNulo = permiteNulo;
		this.campoIndice = campoIndice;
		this.descripcionCampo = descripcionCampo;
		this.tablasDTO = tablasDTO;
		this.camposKey = camposKey;
	}



	public TablasDTO getTablasDTO() {
		return tablasDTO;
	}

	public void setTablasDTO(TablasDTO tablasDTO) {
		this.tablasDTO = tablasDTO;
	}


	/**
	 * @return the nameCampo
	 */
	public String getNameCampo() {
		return nameCampo;
	}

	/**
	 * @param nameCampo the nameCampo to set
	 */
	public void setNameCampo(String nameCampo) {
		this.nameCampo = nameCampo;
	}

	/**
	 * @return the tipoDato
	 */
	public String getTipoDato() {
		return tipoDato;
	}

	/**
	 * @param tipoDato the tipoDato to set
	 */
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	/**
	 * @return the l```ongCampo
	 */
	public int getLongCampo() {
		return longCampo;
	}

	/**
	 * @param longCampo the longCampo to set
	 */
	public void setLongCampo(int longCampo) {
		this.longCampo = longCampo;
	}

	/**
	 * @return the permiteNulo
	 */
	public String getPermiteNulo() {
		return permiteNulo;
	}

	/**
	 * @param permiteNulo the permiteNulo to set
	 */
	public void setPermiteNulo(String permiteNulo) {
		this.permiteNulo = permiteNulo;
	}

	/**
	 * @return the campoIndice
	 */
	public String getCampoIndice() {
		return campoIndice;
	}

	/**
	 * @param campoIndice the campoIndice to set
	 */
	public void setCampoIndice(String campoIndice) {
		this.campoIndice = campoIndice;
	}

	@Override
	public CamposTabla toEntity() {
		CamposTabla camposTabla = new CamposTabla();
		
		BeanUtils.copyProperties(this,camposTabla);
		
		
		return camposTabla;
	}

	@Override
	public CamposTabla toEntity(CamposTabla entity) {
		CamposTabla camposTabla = new CamposTabla();
		
		BeanUtils.copyProperties(entity, camposTabla);
		return camposTabla;
	}

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the descripcionCampo
	 */
	public String getDescripcionCampo() {
		return descripcionCampo;
	}
	
	public CamposTablasKey getCamposKey() {
		return camposKey;
	}



	public void setCamposKey(CamposTablasKey camposKey) {
		this.camposKey = camposKey;
	}



	@Override
	public String toString() {
		return "CamposTablasDTO [camposKey=" + camposKey + ", nameCampo="
				+ nameCampo + ", tipoDato=" + tipoDato + ", longCampo="
				+ longCampo + ", permiteNulo=" + permiteNulo + ", campoIndice="
				+ campoIndice + ", descripcionCampo=" + descripcionCampo
				+ ", tablasDTO=" + tablasDTO + "]";
	}



	/**
	 * @param descripcionCampo the descripcionCampo to set
	 */
	public void setDescripcionCampo(String descripcionCampo) {
		this.descripcionCampo = descripcionCampo;
	}

}
