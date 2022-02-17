package com.personal.diccionariobd.modelodatos.servicios.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;





import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasourceKey;
import com.personal.diccionariobd.modelodatos.repositorios.IBaseDatosRepositorio;
import com.personal.diccionariobd.modelodatos.servicios.IBaseDatosService;

@Service
public class BaseDatosServiceImpl implements IBaseDatosService {
	
	private static final Logger log = LoggerFactory.getLogger(BaseDatosServiceImpl.class);
	
	@Autowired
	private IBaseDatosRepositorio baseDatoseRepositorio;

	@Override
	public Page<BaseDatosDatasource> consultaAllBD(Pageable pageable)
			throws Exception, Throwable {
		// TODO Auto-generated method stub
		//return  BaseDatoseRepositorio.findAll(pageable);
		BaseDatosDatasourceKey prueba = new BaseDatosDatasourceKey(8,"1");
		
		BaseDatosDatasource prueba1 = new BaseDatosDatasource();
		
		prueba1.setId(prueba);
		prueba1.setNombreBd("prueba888act");
		
		baseDatoseRepositorio.save(prueba1);
		
		return baseDatoseRepositorio.findById(prueba, pageable);
	}


	@Override
	public void crearBD(BaseDatosDatasource dato) throws Exception, Throwable {
		baseDatoseRepositorio.save(dato);

	}

	@Override
	public void updateBD(BaseDatosDatasource dato) throws Exception, Throwable {
		baseDatoseRepositorio.save(dato);

	}

	@Override
	public void eliminarBD(BaseDatosDatasource dato) throws Exception,
			Throwable {
		baseDatoseRepositorio.delete(dato);

	}
/*
	@Override
	public BaseDatosDatasource findById(String id) throws Exception, Throwable {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	@Override
	public Page<BaseDatosDatasource> consultaIdDataSources(
			String idDataSources, Pageable pageable) throws Exception,
			Throwable {
		// TODO Auto-generated method stub
		
				
		return baseDatoseRepositorio.findByIdDataSources(idDataSources, pageable);
	}

	@Override
	public BaseDatosDatasource consultaBD(String dataSources, int idBd)
			throws Exception, Throwable {
		BaseDatosDatasourceKey keyData = new BaseDatosDatasourceKey(idBd,dataSources);
		
		//return baseDatoseRepositorio.findByDataSourcesBD(dataSources, idBd);
		return baseDatoseRepositorio.findByIdIdDatasourceAndIdIdBd(dataSources, idBd);
	}

}
