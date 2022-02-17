package com.personal.diccionariobd.modelodatos.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;


public interface IDataSources {

	public Page<DataSources> consultaAllTablas(Pageable pageable);
	
	public DataSources consultatabla(int dato);
	
	public void crearTabla(DataSources dato) ;
	
	public void updateTabla(DataSources dato);
	
	public void eliminarTabla(DataSources dato);

	DataSources findById(String id);
}
