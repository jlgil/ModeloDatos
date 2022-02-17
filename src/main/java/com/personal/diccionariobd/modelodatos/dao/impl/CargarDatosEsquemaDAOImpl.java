package com.personal.diccionariobd.modelodatos.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.personal.diccionariobd.modelodatos.dao.ICargarDatosEsquemaDAO;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasourceKey;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDato;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;
import com.personal.diccionariobd.modelodatos.repositorios.IColumnasTablasEsquema;
import com.personal.diccionariobd.modelodatos.repositorios.ITablasEsquema;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperColumnasTablasEsquemas;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperGenerico;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperTablasEsquemas;
import com.personal.diccionariobd.modelodatos.servicios.IBaseDatosService;

@Repository
public class CargarDatosEsquemaDAOImpl implements ICargarDatosEsquemaDAO {
	
	private static final Logger log = LoggerFactory.getLogger(CargarDatosEsquemaDAOImpl.class);	
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired 
	private IColumnasTablasEsquema columnasTablasEsquema;
	
	@Autowired
	private ITablasEsquema tablasEsquemas;
	
	@Autowired
	private IBaseDatosService baseDatos;

	@Override
	public List<TablasBasedato> listarTablasEsquemas(String idDataSources, int idBaseDatos) {
/*		tablas.setDataSensible(rs.getString("datasensible"));
		tablas.setDescripcion(rs.getString("descripcion"));
		tablas.setFechaCreacion(rs.getDate("fechacreacion"));
		tablas.setFrecuenciaRespaldo(rs.getString("frecuenciarespaldo"));
		tablas.setNombreTabla(rs.getString("nombretabla"));
		tablas.setTipoParticion(rs.getString("tipoparticion"));
		tablas.setTipoTabla(rs.getString("tipotabla"));*/
		
		String query= "SELECT '' as datasensible, ";
		query = query + " '' as descripcion, ";
		query = query + " table_catalog as nombrebasedatos, ";
		query = query + "'01-01-1900' as fechacreacion, ";
		query = query + "'' as frecuenciarespaldo, ";
		query = query + "table_name as nombretabla, ";
		query = query + "'' as tipotabla, " ;
		query = query + "'' as tipoparticion " ;
		
		query = query + "FROM information_schema.tables "; 
		query = query + "WHERE table_schema='public' ";
		query = query + "AND table_type='BASE TABLE'";
		
		log.info("Query a Ejecutar: " + query);
		log.info("ID DataOSurces : " + idDataSources + " Id Base Datos: "  + idBaseDatos);
		
		List<TablasBasedato> tablasBaseDatos = null;
		
		tablasBaseDatos = template.query(query, new RowMapperTablasEsquemas(idDataSources,idBaseDatos));
		
		//Borrar la tabla antes de insertar
		//tablasEsquemas.deleteAll(tablasBaseDatos);
		//Por cada tabla Setear los camps de la tabla
		int cont = 1;
        for  (Iterator iterator = tablasBaseDatos.iterator(); iterator.hasNext();) {
			TablasBasedato tablasBasedato = (TablasBasedato) iterator.next();
			
			 List<CamposTablasBaseDato> campos = listarCamposTablasEsquema(tablasBasedato);
			 
			 tablasBasedato.setCampoTablasEsquema(campos);
			 
			 //Almacenar Base datos
			 if (cont==1){
				 BaseDatosDatasourceKey bdKey = new BaseDatosDatasourceKey(idBaseDatos,idDataSources);
				 //bdKey.setIdBd(idBaseDatos);
				 //bdKey.setIdDatasource(idDataSources);
				 
				 BaseDatosDatasource based = new BaseDatosDatasource();
				 
				 based.setId(bdKey);
				 based.setNombreBd(tablasBasedato.getNombreBaseDatos());
				 
				 try {
					baseDatos.crearBD(based);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.info("Error al Crear Base de datos" + e.getMessage());
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 //cont = cont + 1;
			 
			 cont++;
			//Almancenar la tablas
			tablasEsquemas.save(tablasBasedato);
			
			//Almacernar los campos de la tablas
			columnasTablasEsquema.saveAll(campos);
			
		}
		
		//Setera los Campos de la Tabla
		
		return tablasBaseDatos;
		//return null;
	}

	@Override
	public List<CamposTablasBaseDato> listarCamposTablasEsquema(
			TablasBasedato tablas) {
		List<CamposTablasBaseDato> campos = null;
		
		String idDatasources = tablas.getTablasBaseDatosKey().getIdDatasource();
		int idBD = tablas.getTablasBaseDatosKey().getIdBd();
		int idTabla = tablas.getTablasBaseDatosKey().getIdTabla();
		
		String select = null;
		
		select = "select '0' as id_datasource, ";
		select = select + "0 as id_bd, ";
		select = select + "0 as id_tabla, ";
		select = select + "ordinal_position as id_campo, ";
		select = select + "column_name as nombre_campo, ";
		select = select + "data_type as tipo_dato, ";
		select = select + "case when character_maximum_length > 0 then character_maximum_length else 0 end as longitud_campo, ";
		select = select + "case is_nullable when 'YES' then 'S' else 'N' end  as permite_nulo, ";
		select = select + "'' as campo_indice, ";
		select = select + "'' as descripcion_campo ";
		select = select + "from INFORMATION_SCHEMA.COLUMNS where table_name ='" + tablas.getNombreTabla() + "'";
		select = select + " order by ordinal_position";
		
		log.info("Select para cargar los campos: " + select);
		
		campos = template.query(select, new RowMapperColumnasTablasEsquemas(idDatasources,idBD,idTabla));
		
		
		//grabarColumnas(campos);
		
		
		return campos;
	}
	
	private void grabarColumnas(List<CamposTablasBaseDato> campos){
		
		for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
			CamposTablasBaseDato camposTablasBaseDato = (CamposTablasBaseDato) iterator
					.next();
		
			columnasTablasEsquema.save(camposTablasBaseDato);
		}
		
		
	}

}
