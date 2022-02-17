package com.personal.diccionariobd.modelodatos.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;

public interface IDataSourcesRepositorio extends PagingAndSortingRepository<DataSources,String>{
	Page<DataSources> findBynombreDataSourceContainingAllIgnoreCase(
			String nombreDataSource,
			Pageable pageable);
	
	DataSources findByidDatasource(String idDatasource);
}
