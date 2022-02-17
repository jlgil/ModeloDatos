package com.personal.diccionariobd.modelodatos.servicios;

import java.util.List;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasDTO;


public interface IColumnasTablas {
	
	public List<CamposTablasDTO> consultaAllTablas();
	
	public CamposTablasDTO consultaCampos();
	
	public void crearCampos(CamposTablasDTO dato);
	
	public void updateCampos(CamposTablasDTO dato);
	
	public void eliminarCampos(int dato);
	
	public List<CamposTablasDTO> consultaCamposTabla(int idTabla);
	
	public CamposTablasDTO consultaCamposTablaColumna(int idTabla,int idColumna);


}
