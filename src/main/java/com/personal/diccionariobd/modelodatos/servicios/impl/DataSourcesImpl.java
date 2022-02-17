package com.personal.diccionariobd.modelodatos.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.repositorios.IDataSourcesRepositorio;
import com.personal.diccionariobd.modelodatos.servicios.IDataSources;

@Service
public class DataSourcesImpl implements IDataSources{
	
	@Autowired
	private IDataSourcesRepositorio datasourceRepositorio;

	@Override
	public Page<DataSources> consultaAllTablas(Pageable pageable) {
		return datasourceRepositorio.findAll(pageable);
		
	}

	@Override
	public DataSources consultatabla(int dato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearTabla(DataSources dato) {
		datasourceRepositorio.save(dato);
		
	}

	@Override
	public void updateTabla(DataSources dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarTabla(DataSources dato) {
		datasourceRepositorio.delete(dato);
		
	}
	
	@Override
	public DataSources findById(String id){
		return null;
		
	}
	
	

}
