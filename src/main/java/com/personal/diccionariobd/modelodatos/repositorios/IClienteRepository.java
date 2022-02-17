package com.personal.diccionariobd.modelodatos.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.model.Tablas;

public interface IClienteRepository extends PagingAndSortingRepository<TablasDTO, Integer> {

	Page<TablasDTO> findByNombreTablaContainingAllIgnoreCase(
			String nombreTabla,
			Pageable pageable);
}
