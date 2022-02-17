package com.personal.diccionariobd.modelodatos.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.personal.diccionariobd.modelodatos.dao.ITablasDAO;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.excepciones.ApplicationException;
import com.personal.diccionariobd.modelodatos.model.Tablas;
import com.personal.diccionariobd.modelodatos.repositorios.IClienteRepository;

@Repository(value="implJPA")
public class TablasDaoJPAImpl implements ITablasDAO{
	
	@Autowired
	private IClienteRepository clienteRepository;
	




	@Override
	public TablasDTO consultaOneRegistro(int dato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearRegistros(TablasDTO dato) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRegistro(TablasDTO dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarRegistro(int dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TablasDTO> consultaAllRegistros(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;

	}
	
	public Page<TablasDTO> consultaJPA(Pageable pageable){
		return clienteRepository.findAll(pageable);
	}

}
