package com.personal.diccionariobd.modelodatos.dao;

import java.util.List;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDato;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;

public interface ICargarDatosEsquemaDAO {
	
	public List<TablasBasedato> listarTablasEsquemas(String idDataSources, int idBaseDatos );
	
	public List<CamposTablasBaseDato> listarCamposTablasEsquema(TablasBasedato tablas);

}
