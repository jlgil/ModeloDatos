package com.personal.diccionariobd.modelodatos.servicios;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;


public interface ITablasService {
	
	public List<TablasDTO> consultaAllTablas(Pageable pageable);
	
	public TablasDTO consultatabla(int dato);
	
	public void crearTabla(TablasDTO dato) ;
	
	public void updateTabla(TablasDTO dato);
	
	public void eliminarTabla(int dato);

}
