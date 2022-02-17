package com.personal.diccionariobd.modelodatos.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.personal.diccionariobd.modelodatos.dao.dto.AbstracDTO;
import com.personal.diccionariobd.modelodatos.excepciones.ApplicationException;


public interface IGenericaDAO  <T extends AbstracDTO>{
	
	public List<T> consultaAllRegistros(Pageable pageable);
	
	public T consultaOneRegistro(int dato);
	
	public void crearRegistros(T dato) throws ApplicationException;
	
	public void updateRegistro(T dato);
	
	public void eliminarRegistro(int dato);

}
