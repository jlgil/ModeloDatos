package com.personal.diccionariobd.modelodatos.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.personal.diccionariobd.modelodatos.conexionbd.DBContextHolder;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasBaseDato;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasDTO;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.Menu;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasBasedato;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.dao.dto.menuItem;
import com.personal.diccionariobd.modelodatos.model.Usuario;
import com.personal.diccionariobd.modelodatos.servicios.ICargarDatosEsquemaService;
import com.personal.diccionariobd.modelodatos.servicios.IDBChangeService;
import com.personal.diccionariobd.modelodatos.servicios.IDataSources;

@Controller
@RequestMapping(path = "/mantenimiento")
public class MantenimientoController {
	
	private static final Logger log = LoggerFactory.getLogger(MantenimientoController.class);
	
	@Autowired
	private IDataSources dataSources;
	
    @Autowired
    private IDBChangeService dbChangeServiceImpl;
    
    @Autowired 
    private ICargarDatosEsquemaService cargarDatosEsquema;
    
	//@GetMapping("/pagina/{numeroPagina}/{campoOrden}/{sentidoOrden}")
	@GetMapping("/home")
	public ModelAndView home(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value) {
		
		log.info("Inicio de List de Datasources");

		ModelAndView mav = new ModelAndView("/mantenimiento/home");
		
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
	/**
	 * Ver ciudades.
	 *
	 * @param numeroPagina the numero pagina
	 * @param campoOrden   the campo orden
	 * @param sentidoOrden the sentido orden
	 * @return the model and view
	 */
	//@GetMapping("/pagina/{numeroPagina}/{campoOrden}/{sentidoOrden}")
	@GetMapping("/list")
	public ModelAndView verDataSources(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value) {
		
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
	
	/**
	 * Obtener DataSources.
	 *
	 * @param numeroPagina  the numero pagina
	 * @param tamanioPagina the tamanio pagina
	 * @param campoOrden    the campo orden
	 * @param sentidoOrden  the sentido orden
	 * @return the page
	 */

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
	
   @GetMapping("/form")
	public ModelMap consultaForm(@RequestParam(value = "id", required = false) DataSources datasources, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		//model.addAttribute("username", "joe");

		log.info("INICIANDO ANTES SAVEDATSOURCES OBJETO: " + datasources);
		if (datasources == null) {
			datasources = new DataSources();
		}
		return new ModelMap("DataSources", datasources);
	}
   
	@Transactional
	@PostMapping("/form")
	public String saveDataSources(@ModelAttribute("DataSources") @Valid DataSources tablas, BindingResult result, SessionStatus status) {
		
		log.info("INICIANDO METODO SAVEDATSOURCES OBJETO A GRABAR:" +  tablas);
		//new ClienteValidator().validate(cliente, result);

		if (result.hasErrors()) {

			List<FieldError> listerror = result.getFieldErrors();
			
			for  (Iterator iterator = listerror.iterator(); iterator.hasNext();) {
				FieldError fieldError = (FieldError) iterator.next();
				
				log.info("Error: " + fieldError.getDefaultMessage() + " Campo " + fieldError.getField() );
				
			}
			
			return "datasources/form";
		}
       
		
		log.info("Objeto a ingresar:" + tablas.toString());
		dataSources.crearTabla(tablas);
		status.setComplete();
   
		return "redirect:/datasources/list";
	}
	
	@GetMapping("/delete")
	public ModelMap deleteConfirm(@RequestParam(value = "id", required = true) DataSources tablas, Model model) {
		log.info("Metodo GetMapping delete Objeto:" + tablas.toString());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		//model.addAttribute("username", "joe");
		return new ModelMap("DataSources", tablas);
	}


	@PostMapping("/delete")
	public Object delete(@ModelAttribute("DataSources") DataSources tablas, SessionStatus status) {
		try {
			log.info("Por el Post del DElete");
			dataSources.eliminarTabla(tablas);
		}
		catch (DataIntegrityViolationException exception) {
			log.info("Por el error Post del DElete");
			status.setComplete();
			
			return new ModelAndView("error/errorHapus")
					.addObject("entityId", tablas.getIdDatasource())
					.addObject("entityName", "DataSources")
					.addObject("errorCause", exception.getRootCause().getMessage())
					.addObject("backLink", "/datasources/list");
		}
		status.setComplete();
		return "redirect:/datasources/list";
	}
	
	@GetMapping("/bajartablas")
	public ModelMap bajarConfirm(@RequestParam(value = "id", required = true) DataSources tablas, @RequestParam(value = "mensaje", required = false) String mensaje,Model model) {
		log.info("GetMaaping bajartablas mensaje" + mensaje + "Objeto:" + tablas.toString());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		model.addAttribute("mensaje", mensaje);
		//model.addAttribute("username", "joe");
		return new ModelMap("DataSources", tablas);
	}

	@Transactional
	@PostMapping("/bajartablas")
	public Object bajarTablas(@ModelAttribute("DataSources") DataSources tablas, SessionStatus status, RedirectAttributes attr) throws Throwable {
		String mensaje = "Numeros de Tablas procesadas en el esquema: ";
		
		try {
			log.info("Por el Post del bajar tablas:" +tablas.toString());
			
			int tablasProcesadas = 0;
			
			
			
			if (tablas.getIdDatasource()==null){
				return new Throwable("Error no Existe ID de DataSOurces");
			}
			
			dbChangeServiceImpl.changeDb(tablas.getIdDatasource());	
			
			
			List<TablasBasedato> tablaEsquemas = null;
			
			tablaEsquemas = cargarDatosEsquema.listarTablasEsquemas(tablas.getIdDatasource(),1);
			
			for  (Iterator iterator = tablaEsquemas.iterator(); iterator
					.hasNext();) {
				TablasBasedato tablasBasedato = (TablasBasedato) iterator
						.next();
				
				log.info("Nombre de Tabla: " + tablasBasedato.getNombreTabla() + " ID Tabla:" + tablasBasedato.getTablasBaseDatosKey().getIdTabla());
				
				for (Iterator iterator2 = tablasBasedato.getCampoTablasEsquema().iterator(); iterator2
						.hasNext();) {
					CamposTablasBaseDato campsBasedato = (CamposTablasBaseDato) iterator2
							.next();
					
					log.info("Nombre campo: " + campsBasedato.getNombreCampo() + " Tipo Campo: " + campsBasedato.getTipoDato() );
					
				}
				
			}
			
			if (tablaEsquemas.size() > 0){
				tablasProcesadas = tablaEsquemas.size();
			}

			log.info("Numero de Tablas Procesadas " + tablasProcesadas);
			
			mensaje = mensaje + String.valueOf(tablasProcesadas) ;
			
			 //Eliminar conexiones a la BD
			 dbChangeServiceImpl.cerrarBD(tablas.getIdDatasource());
			 
	          // Volver a la fuente de datos principal
			 DBContextHolder.clearDataSource();
			 
		
		}
		catch (DataIntegrityViolationException exception) {
			log.info("Por el error Post del DElete " + exception.getMessage());
			status.setComplete();
			
			return new ModelAndView("error/errorHapus")
					.addObject("entityId", tablas.getIdDatasource())
					.addObject("entityName", "DataSources")
					.addObject("errorCause", exception.getRootCause().getMessage())
					.addObject("backLink", "/datasources/list");
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ModelAndView("error/errorHapus")
			.addObject("entityId", tablas.getIdDatasource())
			.addObject("entityName", "DataSources")
			.addObject("errorCause", e.getCause().getMessage())
			.addObject("backLink", "/datasources/list");
			
			
		}
		catch (Throwable e) {
			log.info("Capturando el Error en idDataSource:" + tablas.getIdDatasource() + " Mensaje de Error: " + e.getMessage());
			e.printStackTrace();
			
			return new ModelAndView("error/errorHapus")
			.addObject("entityId", tablas.getIdDatasource())
			.addObject("entityName", "DataSources")
			.addObject("errorCause", e.getMessage())
			.addObject("backLink", "/datasources/list");
			
			
		}
		
		status.setComplete();
		attr.addAttribute("id", tablas.getIdDatasource());
		attr.addAttribute("mensaje", mensaje);
		return "redirect:/datasources/bajartablas";
	}

    @ModelAttribute("TipoDataSources")
	public List<String> populateTiposDocumento() {

        List<String> tipoDataSources = new ArrayList<String>();
        tipoDataSources.add("SQLSERVER");
        tipoDataSources.add("POSTGRES");
        tipoDataSources.add("MYSQL");
        tipoDataSources.add("SYBASE");

		return tipoDataSources;
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
