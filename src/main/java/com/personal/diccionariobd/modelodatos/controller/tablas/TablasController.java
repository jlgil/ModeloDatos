package com.personal.diccionariobd.modelodatos.controller.tablas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;
import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasourceKey;
import com.personal.diccionariobd.modelodatos.dao.dto.CamposTablasDTO;
import com.personal.diccionariobd.modelodatos.dao.dto.DataSources;
import com.personal.diccionariobd.modelodatos.dao.dto.Menu;
import com.personal.diccionariobd.modelodatos.dao.dto.TablasDTO;
import com.personal.diccionariobd.modelodatos.dao.dto.menuItem;
import com.personal.diccionariobd.modelodatos.excepciones.ApplicationException;
import com.personal.diccionariobd.modelodatos.repositorios.IClienteRepository;
import com.personal.diccionariobd.modelodatos.servicios.IDataSources;
import com.personal.diccionariobd.modelodatos.servicios.ITablasService;

import org.springframework.web.bind.support.SessionStatus;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

import com.personal.diccionariobd.modelodatos.model.Usuario;


@Controller
@RequestMapping(path = "/tablas")
public class TablasController {
	private static final Logger log = LoggerFactory.getLogger(TablasController.class);
	
	@Autowired
	private ITablasService tablasService;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IDataSources dataSources;
	
	private static final String AJAX_HEADER_NAME = "X-Requested-With";

	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
	
	@GetMapping("/busqueda")
	public ModelAndView busquedaDataSources(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value,
			@RequestParam(value = "idDataSources",required = false) String idDataSources) throws Exception, Throwable {
		
		log.info("Inicio de List de Base de Datos Opcion:" + idDataSources);

		ModelAndView mav = new ModelAndView("/tablas/formbusqueda");
		
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

	
	@GetMapping("/list")
	public ModelMap getAll(@PageableDefault(size = 10) Pageable pageable, @RequestParam(name = "value", required = false) String value, Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		//model.addAttribute("username", "joe");

		if (value != null) {
			model.addAttribute("key", value);
			
			//Integer codigo = Integer.valueOf(value);

			//return new ModelMap().addAttribute("tablas", tablasService.consultatabla(codigo));
			return new ModelMap().addAttribute("tablas", clienteRepository.findByNombreTablaContainingAllIgnoreCase(value, pageable));
		}
		else {
		
			//return new ModelMap().addAttribute("tablas", tablasService.consultaAllTablas(pageable));
			return new ModelMap().addAttribute("tablas", clienteRepository.findAll(pageable));
		}
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
	@GetMapping("/listnew")
	public ModelAndView verCiudades(@RequestParam(value = "numeroPagina", required = false) Integer numeroPagina,
			@RequestParam(value = "campoOrden",required = false) String campoOrden, @RequestParam(value = "sentidoOrden",required = false) String sentidoOrden,
			@RequestParam(value = "value",required = false) String value,
			@RequestParam(value = "idDataSources",required = false) String idDataSources,
			@RequestParam(value = "idBaseDatos",required = false) String idBaseDatos) {

		ModelAndView mav = new ModelAndView("/tablas/listnew");
		
		log.info("Mapping: listnew Numero de pagina: " + numeroPagina + "Campo Orden: " + campoOrden);
		log.info("Mapping: listnew IdDataSources: " + idDataSources + " IdBaseDatos: " + idBaseDatos);

		/*
		 * Total de elementos por pagina
		 * */
		int tamanioPagina = 10;
		if (numeroPagina == null)
			numeroPagina = 1;
		
		campoOrden = "idtabla";
		sentidoOrden = "asc";

		Page<TablasDTO> page = obtenerCiudades(numeroPagina, tamanioPagina, campoOrden, sentidoOrden,value);

		List<TablasDTO> listaCiudad = page.getContent();

		mav.addObject("numeroPagina", numeroPagina);
		mav.addObject("totalPaginas", page.getTotalPages());
		mav.addObject("totalElementos", page.getTotalElements());
		mav.addObject("tablas", listaCiudad);

		mav.addObject("campoOrden", campoOrden);
		mav.addObject("sentidoOrden", sentidoOrden);
		mav.addObject("tipoSentidoOrden", sentidoOrden.equals("asc") ? "desc" : "asc");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("username", ((Usuario)principal).getCodUsuario());
		

		log.info("Mapping: listnew Muestra vista index.html con resutados");

		return mav;

	}
	

	/**
	 * Obtener ciudades.
	 *
	 * @param numeroPagina  the numero pagina
	 * @param tamanioPagina the tamanio pagina
	 * @param campoOrden    the campo orden
	 * @param sentidoOrden  the sentido orden
	 * @return the page
	 */

	public Page<TablasDTO> obtenerCiudades(int numeroPagina, int tamanioPagina, String campoOrden, String sentidoOrden,String value) {

		Pageable pageable = PageRequest.of(numeroPagina - 1, tamanioPagina,
				sentidoOrden.equals("asc") ? Sort.by(campoOrden).ascending() : Sort.by(campoOrden).descending());
		
		if (value != null) {
			return clienteRepository.findByNombreTablaContainingAllIgnoreCase(value, pageable);
		}
		else {
			Page<TablasDTO> list = clienteRepository.findAll(pageable);
			
			
			List<TablasDTO> listaDTO = list.getContent();
			
			for (Iterator iterator = listaDTO.iterator(); iterator.hasNext();) {
				TablasDTO tablasDTO = (TablasDTO) iterator.next();
				
				log.info("Lista de Tablas ID tabla: " + tablasDTO.getIdtabla() + " Nombre: " + tablasDTO.getNombreTabla()); 
				
			
				List<CamposTablasDTO> listaColumnas = null;
				listaColumnas = tablasDTO.getDireccionList();
				
			    /*for (Iterator iterator2 = listaColumnas.iterator(); iterator2
						.hasNext();) {
					CamposTablasDTO camposTablasDTO = (CamposTablasDTO) iterator2
							.next();
					
					log.info("Lista de Campos CodigoTabla: " + camposTablasDTO.getCamposKey().getCodTabla() + " Campo: " + camposTablasDTO.getNameCampo() + " Desrip: " + camposTablasDTO.getDescripcionCampo() + " Codigo Campo: " + camposTablasDTO.getCamposKey().getIdCampo());
					
				}*/
				
			}
			
			
			
			
					

			
			return clienteRepository.findAll(pageable);
		}		

		
	}


    @GetMapping("/form")
	public ModelMap getById(@RequestParam(value = "id", required = false) TablasDTO tablas, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		//model.addAttribute("username", "joe");
		
		log.info("Form GetMapping: " + tablas);

		if (tablas == null) {
			tablas = new TablasDTO();
		}
		return new ModelMap("TablasDTO", tablas);
	}


	@Transactional
	@PostMapping("/form")
	public String saveSocio(@ModelAttribute("TablasDTO") @Valid TablasDTO tablas, BindingResult result, SessionStatus status) {

		//new ClienteValidator().validate(cliente, result);

		if (result.hasErrors()) {

			List<FieldError> listerror = result.getFieldErrors();
			
			for  (Iterator iterator = listerror.iterator(); iterator.hasNext();) {
				FieldError fieldError = (FieldError) iterator.next();
				
				log.info("Error: " + fieldError.getDefaultMessage() + " Campo " + fieldError.getField() );
				
			}
			
            System.out.println("En el error Nombre tabla" + tablas.getNombreTabla());
			return "tablas/form";
		}
        
		
		
		clienteRepository.save(tablas);
		status.setComplete();
    
		return "redirect:/tablas/listnew";
	}
	
	@GetMapping("/delete")
	public ModelMap deleteConfirm(@RequestParam(value = "id", required = true) TablasDTO tablas, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", ((Usuario)principal).getCodUsuario());
		//model.addAttribute("username", "joe");
		return new ModelMap("TablasDTO", tablas);
	}


	@PostMapping("/delete")
	public Object delete(@ModelAttribute("TablasDTO") TablasDTO tablas, SessionStatus status) {
		try {
			log.info("Por el Post del DElete");
			clienteRepository.delete(tablas);
		}
		catch (DataIntegrityViolationException exception) {
			log.info("Por el error Post del DElete");
			status.setComplete();
			
			return new ModelAndView("error/errorHapus")
					.addObject("entityId", tablas.getIdtabla())
					.addObject("entityName", "TablasDTO")
					.addObject("errorCause", exception.getRootCause().getMessage())
					.addObject("backLink", "/tablas/listnew");
		}
		status.setComplete();
		return "redirect:/tablas/listnew";
	}

	




    @ModelAttribute("allFrecuenciaRespaldo")
	public List<String> populateTiposDocumento() {

        List<String> frecuenciaRespaldo = new ArrayList<String>();
        frecuenciaRespaldo.add("DIARIO");
        frecuenciaRespaldo.add("MENSUAL");
        frecuenciaRespaldo.add("ANUAL");
        frecuenciaRespaldo.add("TRIMESTRAL");

        log.info("DEVOLVIENDO FRECUENCIA DE RSPALDOS");
		return frecuenciaRespaldo;
	}


	
	// Ajax Methods
	@PostMapping(params = "addDireccion", path = { "/clientes/form/ajax/direcciones", "/clientes/form/ajax/direcciones/{codSocio}" })
	public String addDireccion(TablasDTO cliente,
			HttpServletRequest request, Model model) {

		addDireccionACliente(cliente);
		log.info("Por el metodo AJAJ addDireccion " + cliente.toString());
		
		
		model.addAttribute("TablasDTO", cliente);

    	if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {

			return "tablas/form::#direcciones";
		}
		else {
			return "tablas/form";
		}
	}
	
	private void addDireccionACliente(TablasDTO cliente) {
		cliente.getDireccionList().add(new CamposTablasDTO(
				cliente.getDireccionNueva().getCamposKey().getCodTabla(),
				cliente.getDireccionNueva().getCamposKey().getIdCampo(),
				cliente.getDireccionNueva().getNameCampo(),
				cliente.getDireccionNueva().getTipoDato(),
    			cliente.getDireccionNueva().getLongCampo(),
				cliente.getDireccionNueva().getPermiteNulo(),

				cliente.getDireccionNueva().getCampoIndice(),
				cliente.getDireccionNueva().getDescripcionCampo(),
				cliente,
				cliente.getDireccionNueva().getCamposKey()
		));
	}
	
	@PostMapping(params = "removeDireccion", path = { "/clientes/form/ajax/direcciones", "/clientes/form/ajax/direcciones/{codSocio}" })
	public String removeDireccion(TablasDTO cliente, @RequestParam("removeDireccion") Integer index, HttpServletRequest request, Model model) {

		removeDireccionByIndex(cliente, index);
		
		model.addAttribute("TablasDTO", cliente);

		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {

			return "tablas/form::#direcciones";
		}
		else {
			return "tablas/form";
		}
	}
	
	@PostMapping(params = "editDireccion", path = { "/clientes/form/ajax/direcciones", "/clientes/form/ajax/direcciones/{codSocio}" })
	public String editDireccion(TablasDTO cliente, @RequestParam("editDireccion") Integer index, HttpServletRequest request, Model model) {

		modifyDireccionACliente(cliente, index);
		
		model.addAttribute("TablasDTO", cliente);

		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {

			return "/tablas/form::#direcciones";
		}
		else {
			return "/tablas/form";
		}
	}

	private void modifyDireccionACliente(TablasDTO cliente, Integer index) {

		CamposTablasDTO direccionActual = cliente.getDireccionList().get(index);
/*		direccionActual.setCodTabla(cliente.getDireccionNueva().getCodTabla());
		direccionActual.setIdCampo(cliente.getDireccionNueva().getIdCampo());*/
		direccionActual.setCamposKey(cliente.getDireccionNueva().getCamposKey());
		direccionActual.setCampoIndice(cliente.getDireccionNueva().getCampoIndice());
		direccionActual.setDescripcionCampo(cliente.getDireccionNueva().getDescripcionCampo());		
		direccionActual.setLongCampo(cliente.getDireccionNueva().getLongCampo());
		direccionActual.setNameCampo(cliente.getDireccionNueva().getNameCampo());
		direccionActual.setPermiteNulo(cliente.getDireccionNueva().getPermiteNulo());
		direccionActual.setTablasDTO(cliente);
		direccionActual.setTipoDato(cliente.getDireccionNueva().getTipoDato());
	}

	private void removeDireccionByIndex(TablasDTO cliente, @RequestParam("removeDireccion") Integer index) {
		List<CamposTablasDTO> filteredList =
				IntStream.range(0, cliente.getDireccionList().size())
						.filter(i -> i != index)
						.mapToObj(cliente.getDireccionList()::get)
						.collect(toList());
		cliente.setDireccionList(filteredList);
	}
	
	@GetMapping("/clientes/form/ajax/distritos")
	public String ajaxDistritos(@RequestParam(name = "idDataSources", required = false, defaultValue = "0") String codUbigeo, Model model) {
		
		log.info("Entro por el Metodo AJAX, Paramero: " + codUbigeo);
		List<BaseDatosDatasource> listbd = new ArrayList<>() ;
		if (!codUbigeo.equals("0")) {
			//String codDpto = codUbigeo.substring(0, 2);
			//String codProvincia = codUbigeo.substring(2, 4);
			//List<Ubigeo> distritos = new ArrayList<>();
			//distritos.add(new Ubigeo("0", "--Seleccione--"));
			//ubigeoRepository.findDistritos(codDpto, codProvincia).stream().forEach(e -> distritos.add(e));
			
			BaseDatosDatasource prueba = new BaseDatosDatasource();
			BaseDatosDatasourceKey pruebakey = new BaseDatosDatasourceKey();
			pruebakey.setIdBd(5);
			pruebakey.setIdDatasource(codUbigeo);
			
			prueba.setId(pruebakey);
			prueba.setNombreBd("Prueba");
			
			listbd.add(prueba);
						
			
			
			model.addAttribute("distritos", listbd);
		}
		else {
			//List<Ubigeo> distritos = new ArrayList<>();
			//distritos.add(new Ubigeo("0", "--Seleccione--"));
			model.addAttribute("distritos", listbd);
		}
		return "tablas/formbusqueda::distritos";
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
		menu3.setUrl("/tablas/busqueda");
		
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


