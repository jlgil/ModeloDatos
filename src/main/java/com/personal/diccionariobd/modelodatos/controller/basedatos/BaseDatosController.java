	package com.personal.diccionariobd.modelodatos.controller.basedatos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasourceKey;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.Menu;
import com.personal.diccionariobd.modelodatos.dao.dto.menuItem;
import com.personal.diccionariobd.modelodatos.model.Usuario;
import com.personal.diccionariobd.modelodatos.servicios.IBaseDatosService;
import com.personal.diccionariobd.modelodatos.servicios.IDataSources;


@Controller
@RequestMapping(path = "/basedatos")
public class BaseDatosController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseDatosController.class);
	
	@Autowired
	private IBaseDatosService baseDatosService;
	
	@Autowired
	private IDataSources dataSources;
	
	@GetMapping("/busqueda")
	public ModelAndView busquedaDataSources(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value,
			@RequestParam(value = "idDataSources",required = false) String idDataSources) throws Exception, Throwable {
		
		log.info("Inicio de List de Base de Datos Opcion:" + idDataSources);

		ModelAndView mav = new ModelAndView("/basedatos/formbusqueda");
		
		/*
		 * Variables para el ordenamiento de la Lista de Para una DataSources dado
		 * */
		int tamanioPagina = 10;
		if (numeroPagina == null)
			numeroPagina = 1;
		
		campoOrden = "idDatasource";
		sentidoOrden = "asc";
		
		Pageable pageable = PageRequest.of(numeroPagina - 1, tamanioPagina,
				sentidoOrden.equals("asc") ? Sort.by(campoOrden).ascending() : Sort.by(campoOrden).descending());
		
		
		//Page<BaseDatosDatasource> page = baseDatosService.consultaIdDataSources(idDataSources, pageable);
		Page<DataSources> page = dataSources.consultaAllTablas(pageable);
		List<DataSources> listaCiudad = page.getContent();
		
	
		mav.addObject("numeroPagina", numeroPagina);
		mav.addObject("totalPaginas", page.getTotalPages());
		mav.addObject("totalElementos", page.getTotalElements());
		mav.addObject("tablas", listaCiudad);
    	mav.addObject("campoOrden", campoOrden);
		mav.addObject("sentidoOrden", sentidoOrden);
		mav.addObject("tipoSentidoOrden", sentidoOrden.equals("asc") ? "desc" : "asc");
				
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", ((Usuario)principal).getCodUsuario());
		

		return mav;

	}
	
	
	@GetMapping("/listdatasources")
	public ModelAndView listaDataSources(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value) throws Exception, Throwable {
		
		log.info("Inicio de List de Datasources");

		ModelAndView mav = new ModelAndView("/datasources/list");
		
		log.info("Numero de pagina: " + numeroPagina);
		log.info("Campo ordern: " + campoOrden);

		/*
		 * Total de elementos por pagina
		 * */
		int tamanioPagina = 10;
		if (numeroPagina == null)
			numeroPagina = 1;
		
		campoOrden = "idDatasource";
		sentidoOrden = "asc";

		Page<DataSources> page = obtenerDataSources(numeroPagina, tamanioPagina, campoOrden, sentidoOrden,value);

		List<DataSources> listaCiudad = page.getContent();

		mav.addObject("numeroPagina", numeroPagina);
		mav.addObject("totalPaginas", page.getTotalPages());
		mav.addObject("totalElementos", page.getTotalElements());
		mav.addObject("tablas", listaCiudad);

		mav.addObject("campoOrden", campoOrden);
		mav.addObject("sentidoOrden", sentidoOrden);
		mav.addObject("tipoSentidoOrden", sentidoOrden.equals("asc") ? "desc" : "asc");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", ((Usuario)principal).getCodUsuario());
		

		log.info("FIn de List de Datasources");

		return mav;

	}
	
	public Page<DataSources> obtenerDataSources(int numeroPagina, int tamanioPagina, String campoOrden, String sentidoOrden,String value) {

		Pageable pageable = PageRequest.of(numeroPagina - 1, tamanioPagina,
				sentidoOrden.equals("asc") ? Sort.by(campoOrden).ascending() : Sort.by(campoOrden).descending());
		/*
		if (value != null) {
			return dataSources.findByNombreTablaContainingAllIgnoreCase(value, pageable);
		}
		else {*/
			Page<DataSources> list = dataSources.consultaAllTablas(pageable);
			
			
			List<DataSources> listaDTO = list.getContent();
			
			for (Iterator iterator = listaDTO.iterator(); iterator.hasNext();) {
				DataSources tablasDTO = (DataSources) iterator.next();
				
				log.info("Lista de DataSources ID tabla: " + tablasDTO.getIdDatasource() + " Nombre: " + tablasDTO.getNombreDataSource()); 
				
			}
		//}
			return dataSources.consultaAllTablas(pageable);				
	}
	
	
	
	@GetMapping("/list")
	public ModelAndView verDataSources(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value,
			@RequestParam(value = "idDataSources",required = false) String idDataSources) throws Exception, Throwable {
		
		log.info("Inicio de List de Base de Datos Opcion IdDataSources: " + idDataSources);

		ModelAndView mav = new ModelAndView("/basedatos/list");
		
		/*
		 * Variables para el ordenamiento de la Lista de Para una DataSources dado
		 * */
		int tamanioPagina = 10;
		if (numeroPagina == null)
			numeroPagina = 1;
		
		campoOrden = "id.idDatasource";
		sentidoOrden = "asc";
		
		Pageable pageable = PageRequest.of(numeroPagina - 1, tamanioPagina,
				sentidoOrden.equals("asc") ? Sort.by(campoOrden).ascending() : Sort.by(campoOrden).descending());
		
		
		Page<BaseDatosDatasource> page = baseDatosService.consultaIdDataSources(idDataSources, pageable);
		List<BaseDatosDatasource> listaCiudad = page.getContent();
		

		mav.addObject("numeroPagina", numeroPagina);
		mav.addObject("totalPaginas", page.getTotalPages());
		mav.addObject("totalElementos", page.getTotalElements());
		mav.addObject("tablas", listaCiudad);
    	mav.addObject("campoOrden", campoOrden);
		mav.addObject("sentidoOrden", sentidoOrden);
		mav.addObject("tipoSentidoOrden", sentidoOrden.equals("asc") ? "desc" : "asc");
		mav.addObject("idDataSources", idDataSources);
				
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", ((Usuario)principal).getCodUsuario());
		
		log.info("Saliendo de BaseDatosS: " + listaCiudad);
		return mav;

	}
	
	/*OJO ESTE CODIGO AL PASAR EL DI DEDE LIST DA ERROR PORQUE NO RECONOCE LE ID PASADO COMO UN OBJETO BaseDatossource
	   @GetMapping("/form")
		public ModelMap consultaForm(@RequestParam(value = "id", required = false) BaseDatosDatasource baseDatos, Model model) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("username", ((Usuario)principal).getCodUsuario());
			//model.addAttribute("username", "joe");

			log.info("INICIANDO Form: " + baseDatos);
			if (baseDatos == null) {
				baseDatos = new BaseDatosDatasource();
			}
			return new ModelMap("BaseDatosDatasource", baseDatos);
		}
	  
	   */
	
	   @GetMapping("/form")
			public ModelMap consultaForm(@RequestParam(value = "idDatasource", required = false) String datasource, @RequestParam(value = "idBd", required = false) Integer baseDatos,Model model) throws Exception, Throwable {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.addAttribute("username", ((Usuario)principal).getCodUsuario());
				//model.addAttribute("username", "joe");
				
				log.info("INICIANDO Form: id DataSource" + datasource + " Id BD: " + baseDatos);
				
				BaseDatosDatasource baseDatosDatasource = null;
				
				if (datasource==null || baseDatos==null){
					baseDatosDatasource = new BaseDatosDatasource();
					BaseDatosDatasourceKey key = new BaseDatosDatasourceKey();
					key.setIdDatasource(datasource);
					baseDatosDatasource.setId(key);

				}else{
					baseDatosDatasource = baseDatosService.consultaBD(datasource, baseDatos);
				}
				
				//log.info("INICIANDO Form55: " + baseDatosDatasource.getId().getIdDatasource());
				
				return new ModelMap("BaseDatosDatasource", baseDatosDatasource);
			}
	   
		@Transactional
		@PostMapping("/form")
		public String saveDataSources(@ModelAttribute("BaseDatosDatasource") @Valid BaseDatosDatasource tablas, BindingResult result, SessionStatus status,RedirectAttributes attr) throws Exception, Throwable {
			
			log.info("INICIANDO METODO SAVEDATSOURCES OBJETO A DATASO:" +  tablas.getId().getIdDatasource());
			log.info("INICIANDO METODO SAVEDATSOURCES OBJETO A BD:" +  tablas.getId().getIdBd());
			new BaseDatosValidator().validate(tablas, result);

			if (result.hasErrors()) {

				List<FieldError> listerror = result.getFieldErrors();
				
				for  (Iterator iterator = listerror.iterator(); iterator.hasNext();) {
					FieldError fieldError = (FieldError) iterator.next();
					
					log.info("Error: " + fieldError.getDefaultMessage() + " Campo " + fieldError.getField() );
					
				}
				
				return "basedatos/form";
			}
	       
			
			log.info("Objeto a ingresar:" + tablas.toString());
			baseDatosService.crearBD(tablas);
			status.setComplete();
			
			attr.addAttribute ("idDataSources", tablas.getId().getIdDatasource()); // Dirección de salto con parámetros de prueba
	   
			return "redirect:/basedatos/list";
		}
		
		/*POR ERROR DEL ID
		@GetMapping("/delete")
		public ModelMap deleteConfirm(@RequestParam(value = "id", required = true) BaseDatosDatasource tablas, Model model) {
			log.info("Metodo GetMapping delete Objeto:" + tablas.toString());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("username", ((Usuario)principal).getCodUsuario());
			//model.addAttribute("username", "joe");
			return new ModelMap("DataSources", tablas);
		}
		*/
		
		@GetMapping("/delete")
		public ModelMap deleteConfirm(@RequestParam(value = "idDatasource", required = false) String datasource, @RequestParam(value = "idBd", required = false) Integer baseDatos, Model model) throws Exception, Throwable {
			log.info("Metodo GetMapping delete Objeto:");
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("username", ((Usuario)principal).getCodUsuario());
			//model.addAttribute("username", "joe");
			
			log.info("INICIANDO Form: " + baseDatos);
			
			BaseDatosDatasource baseDatosDatasource = null;
			
			if (datasource==null || datasource==""){
				baseDatosDatasource = new BaseDatosDatasource();
			}else{
				baseDatosDatasource = baseDatosService.consultaBD(datasource, baseDatos);
			}
			
			log.info("INICIANDO Form55: " + baseDatosDatasource.getId().getIdDatasource());
			
			return new ModelMap("BaseDatosDatasource", baseDatosDatasource);
		}
		
		@PostMapping("/delete")
		public Object delete(@ModelAttribute("DataSources") BaseDatosDatasource tablas, SessionStatus status,RedirectAttributes attr) throws Exception, Throwable {
			try {
				log.info("Por el Post del DElete");
				baseDatosService.eliminarBD(tablas);
			}
			catch (DataIntegrityViolationException exception) {
				log.info("Por el error Post del DElete");
				status.setComplete();
				
				return new ModelAndView("error/errorHapus")
						.addObject("entityId", tablas.getNombreBd())
						.addObject("entityName", "DataSources")
						.addObject("errorCause", exception.getRootCause().getMessage())
						.addObject("backLink", "/basedatos/list");
			}
			status.setComplete();
			attr.addAttribute ("idDataSources", tablas.getId().getIdDatasource()); // Dirección de salto con parámetros de prueba
			return "redirect:/basedatos/list";
		}



		/****PARTE DEL MENUS SOLO DE EJEMPLO ELIMINAR POSTEIROR**/
		@ModelAttribute("menuItemsList")
		public List<menuItem> menues() {
			log.info("Entro a generar los Menus");
			return agruparMenus( getMenues());
		}
		
		public List<menuItem> agruparMenus(List<Menu> menues) {

			//String grupoMenus = getGrupoMenusDeUsuario();

			List<menuItem> menuItems = new ArrayList<>();
			Integer parentId = 0;
			menuItem menuItemPadre = new menuItem();
			Integer childId = 0;
			List<menuItem> hijos = new ArrayList<>();

			for (Menu menu : menues) {
				if (parentId != menu.getParentId()) {

					//if(menuEstaDisponible(menu.getParentId(), grupoMenus)) {
						hijos = new ArrayList<>();
						menuItemPadre = new menuItem(menu.getParentName(), hijos);
						parentId = menu.getParentId();
						menuItems.add(menuItemPadre);
					//}
				}

				//if(menuEstaDisponible(menu.getChildId(), grupoMenus)) {
					menuItem menuItemHijo = new menuItem();
					menuItemHijo.setName(menu.getChildName());
					menuItemHijo.setUrl(menu.getUrl());
					menuItemHijo.setParent(menuItemPadre);
					menuItemPadre.getChildren().add(menuItemHijo);
				//}
			}

	        //System.out.println("Menus Item: " + menuItems.toString());
			return menuItems;
		}
		
		public List<Menu> getMenues()  {
	/*
			List<Menu> menues = new  ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setParentId(1);
			menu.setParentName("Maestro");
			menu.setChildId(2);
			menu.setChildName("Tablas");
			menu.setUrl("/tablas/list");
			
			Menu menu1 = new Menu();
			menu1.setParentId(1);
			menu1.setParentName("Maestros");
			menu1.setChildId(3);
			menu1.setChildName("Tablas Paginacion");
			menu1.setUrl("/tablas/listnew");
			
			Menu menu2 = new Menu();
			menu2.setParentId(2);
			menu2.setParentName("DataSources");
			menu2.setChildId(1);
			menu2.setChildName("Mantenimiento DataSources");
			menu2.setUrl("/datasources/list");
			
			menues.add(menu);
			menues.add(menu1);
			menues.add(menu2);*/
			
			List<Menu> menues = new  ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setParentId(1);
			menu.setParentName("Mantenimiento");
			menu.setChildId(1);
			menu.setChildName("Mantenimiento");
			menu.setUrl("/mantenimiento/home");
			
			Menu menu1 = new Menu();
			menu1.setParentId(1);
			menu1.setParentName("Mantenimiento");
			menu1.setChildId(2);
			menu1.setChildName("DataSources");
			menu1.setUrl("/datasources/list");
			
			Menu menu2 = new Menu();
			menu2.setParentId(1);
			menu2.setParentName("Mantenimiento");
			menu2.setChildId(3);
			menu2.setChildName("Base de Datos");
			menu2.setUrl("/basedatos/busqueda");
			
			Menu menu3 = new Menu();
			menu3.setParentId(1);
			menu3.setParentName("Mantenimiento");
			menu3.setChildId(4);
			menu3.setChildName("Tablas");
			menu3.setUrl("/tablas/listnew");
			
			Menu menu4 = new Menu();
			menu4.setParentId(2);
			menu4.setParentName("Diccionario");
			menu4.setChildId(1);
			menu4.setChildName("Diccionario");
			menu4.setUrl("/diccionario/home");
			
			
			menues.add(menu);
			menues.add(menu1);
			menues.add(menu2);

			menues.add(menu3);
			menues.add(menu4);

			return menues;
		}
	/***FINDE MENUS******/


	
}
