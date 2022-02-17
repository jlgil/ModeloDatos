package com.personal.diccionariobd.modelodatos.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.personal.diccionariobd.modelodatos.dao.ITablasDAO;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.servicios.ITablasService;


@Service
public class TablasServiceImpl implements ITablasService{
	
	@Autowired
	@Qualifier("implJPA")
	private ITablasDAO tablas;
	

	@Override
	public List<TablasDTO> consultaAllTablas(Pageable pageable) {
		
		return tablas.consultaAllRegistros(pageable);
	}

	@Override
	public TablasDTO consultatabla(int dato) {
		return tablas.consultaOneRegistro(dato);
	}

	@Override
	public void crearTabla(TablasDTO dato)  {
		tablas.crearRegistros(dato);
		
	}

	@Override
	public void updateTabla(TablasDTO dato) {
		tablas.updateRegistro(dato);
		
	}

	@Override
	public void eliminarTabla(int dato) {
		tablas.eliminarRegistro(dato);
		
	}



}
