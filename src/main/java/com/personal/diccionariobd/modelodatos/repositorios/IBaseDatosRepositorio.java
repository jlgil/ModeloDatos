package com.personal.diccionariobd.modelodatos.repositorios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasourceKey;


public interface IBaseDatosRepositorio  extends PagingAndSortingRepository<BaseDatosDatasource,BaseDatosDatasourceKey> {
	
	Page<BaseDatosDatasource> findById(BaseDatosDatasourceKey id ,Pageable pageable);
	
	BaseDatosDatasource findByIdIdDatasourceAndIdIdBd(String idDatasource,int idBd);
	
	BaseDatosDatasource findByIdIdDatasource(String idDatasource);

	@Query ("select l from BaseDatosDatasource l where l.id.idDatasource = :idData and l.id.idBd = :idBd")
	BaseDatosDatasource findByDataSourcesBD(@Param("idData")String idDataSource, @Param("idBd") int idBd);
	
	@Query ("select l from BaseDatosDatasource l where l.id.idDatasource = :nombre")
	Page<BaseDatosDatasource> findByIdDataSources(@Param("nombre") String idDataSource ,Pageable pageable);

}
