package com.personal.diccionariobd.modelodatos.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.personal.diccionariobd.modelodatos.dao.dto.Menu;
import com.personal.diccionariobd.modelodatos.dao.dto.menuItem;
import com.personal.diccionariobd.modelodatos.model.Usuario;

import org.springframework.security.core.userdetails.User;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	  @GetMapping("/home")
	    public String homePage(ModelMap model){
		  log.info("Iniciando Home Controller /home");
		  
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			model.addAttribute("username", ((Usuario)principal).getCodUsuario());
	    	//model.addAttribute("username", "joe");
	    	
			return "home";
	    }
	  
	  @GetMapping("/")
	    public String home(ModelMap model){
		  	log.info("Iniciando Home Controller /");
		  
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			model.addAttribute("username",  ((Usuario) principal).getCodUsuario());
	    	//model.addAttribute("username", "joe");
	    	
			return "home";
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
