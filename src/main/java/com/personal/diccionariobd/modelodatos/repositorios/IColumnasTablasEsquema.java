package com.personal.diccionariobd.modelodatos.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDato;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDatoKey;

public interface IColumnasTablasEsquema extends PagingAndSortingRepository<CamposTablasBaseDato,CamposTablasBaseDatoKey> {

}
