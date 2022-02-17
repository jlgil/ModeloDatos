package com.personal.diccionariobd.modelodatos.dao;

import java.util.List;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasDTO;


public interface ICamposTablasDAO extends IGenericaDAO<CamposTablasDTO> {

	public List<CamposTablasDTO> consultaCamposTabla(int idTabla);
	
	public CamposTablasDTO consultaCamposTablaColumna(int idTabla,int idColumna);
}
