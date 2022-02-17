package com.personal.diccionariobd.modelodatos.controller;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.personal.diccionariobd.modelodatos.conexionbd.DBContextHolder;
import com.personal.diccionariobd.modelodatos.dao.dto.CiuuDto;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.model.Usuario;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperCiuu;
import com.personal.diccionariobd.modelodatos.rowmapper.RowMapperGenerico;
import com.personal.diccionariobd.modelodatos.servicios.IDBChangeService;

@Controller
public class pruebaController {
	
	private static final Logger log = LoggerFactory.getLogger(pruebaController.class);
	
    @Autowired
    private IDBChangeService dbChangeServiceImpl;
    
	@Autowired
	private JdbcTemplate template;
    
	@GetMapping("/basedatos")
	public String homePage(ModelMap model) throws Throwable{
		  log.info("Iniciando basedatos /basedatos");
	  

          // Cambiar a la base de datos dbtest2
		 String datasourceId="1";
		 dbChangeServiceImpl.changeDb(datasourceId);
		 //List<User> userList= userService.queryUserInfo();
		 //System.out.println(userList.toString());
		 
		 List<CiuuDto> ciuu =  getClientes();
		 
		 for (Iterator iterator = ciuu.iterator(); iterator.hasNext();) {
			CiuuDto ciuuDto = (CiuuDto) iterator.next();
			
			log.info("Tabla ciuud COd:" + ciuuDto.getCodigo() );
			log.info("Tabla ciuud Descr:" + ciuuDto.getDescripcion() );
			
		}

		 
		 /*ist<TablasDTO> tablas = getTablas();
		 
		 for (Iterator iterator = tablas.iterator(); iterator.hasNext();) {
			TablasDTO tablasDTO = (TablasDTO) iterator.next();
			log.info("Tabla nomgre:" + tablasDTO.getNombreTabla() );
		}*/
		
		          // Cambiar a la base de datos dbtest3
		 dbChangeServiceImpl.changeDb("2");
		 //List<User> userList3= userService.queryUserInfo();
		 //System.out.println(userList3.toString());
		 
		 List<CiuuDto> ciuu1 =  getClientes();
		 
		 for (Iterator iterator = ciuu1.iterator(); iterator.hasNext();) {
			CiuuDto ciuuDto = (CiuuDto) iterator.next();
			
			log.info("Tabla ciuud COd:" + ciuuDto.getCodigo() );
			log.info("Tabla ciuud Descr:" + ciuuDto.getDescripcion() );
			
		}
		
		 

          // Volver a la fuente de datos principal
		 DBContextHolder.clearDataSource();

		
		return "home";
	}
	
	
	public List<TablasDTO> getTablas() {
		return template.query("select * from tablasbd", new RowMapperGenerico());
		//return null;
	}
	
	public List<CiuuDto> getClientes() {

		return template.query("select * from ciiu", new RowMapperCiuu());
		//return null;
	}
}
