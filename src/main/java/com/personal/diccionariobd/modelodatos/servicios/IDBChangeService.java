package com.personal.diccionariobd.modelodatos.servicios;

import java.util.List;

import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;

public interface IDBChangeService {
	   List<DataSources> get();
	   
	    boolean changeDb(String datasourceId) throws Exception, Throwable;
	    
	    boolean cerrarBD(String datasourceId) throws Exception;
}
