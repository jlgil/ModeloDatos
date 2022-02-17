package com.personal.diccionariobd.modelodatos.dao.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.personal.diccionariobd.modelodatos.dao.IGenericaDAO;
import com.personal.diccionariobd.modelodatos.dao.dto.AbstracDTO;




public class AbstractoDaoImpl<T extends AbstracDTO> implements IGenericaDAO<T> {

	@Override
	public List<T> consultaAllRegistros(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void crearRegistros(T dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRegistro(T dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarRegistro(int dato) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T consultaOneRegistro(int dato) {
		// TODO Auto-generated method stub
		return null;
	}

}
 