package com.personal.diccionariobd.modelodatos.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.diccionariobd.modelodatos.dao.ICargarDatosEsquemaDAO;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;
import com.personal.diccionariobd.modelodatos.repositorios.IColumnasTablasEsquema;
import com.personal.diccionariobd.modelodatos.servicios.ICargarDatosEsquemaService;

@Service
public class CargarDatosEsquemaServiceImpl implements ICargarDatosEsquemaService{

	@Autowired
	private ICargarDatosEsquemaDAO cargardatos;
	
	
	
	@Override
	public List<TablasBasedato> listarTablasEsquemas(String idDataSources, int idBaseDatos) {
		// TODO Auto-generated method stub
		return cargardatos.listarTablasEsquemas(idDataSources,idBaseDatos);
	}

}
