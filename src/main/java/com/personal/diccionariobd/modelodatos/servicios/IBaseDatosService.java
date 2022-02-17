package com.personal.diccionariobd.modelodatos.servicios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;

public interface IBaseDatosService {
	
	public Page<BaseDatosDatasource> consultaAllBD(Pageable pageable) throws Exception, Throwable;
	
	public BaseDatosDatasource consultaBD(String dataSources, int idBd) throws Exception, Throwable;
	
	public void crearBD(BaseDatosDatasource dato) throws Exception, Throwable;
	
	public void updateBD(BaseDatosDatasource dato) throws Exception, Throwable;
	
	public void eliminarBD(BaseDatosDatasource dato) throws Exception, Throwable ;

	Page<BaseDatosDatasource> consultaIdDataSources(String idDataSources,Pageable pageable) throws Exception, Throwable;

}
