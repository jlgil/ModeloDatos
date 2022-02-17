package com.personal.diccionariobd.modelodatos.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.personal.diccionariobd.modelodatos.dao.dto.TablasBaseDatosKey;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;

public interface ITablasEsquema extends PagingAndSortingRepository<TablasBasedato,TablasBaseDatosKey> {

}
