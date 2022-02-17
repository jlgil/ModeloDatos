package com.personal.diccionariobd.modelodatos.servicios;

import java.util.List;

import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;

public interface ICargarDatosEsquemaService {

	public List<TablasBasedato> listarTablasEsquemas(String idDataSources, int idBD);
}
