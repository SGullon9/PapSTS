package org.gullon.pap2022.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.gullon.pap2022.entities.Aficion;
import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.helper.H;
import org.gullon.pap2022.repository.AficionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AficionController {
	
	@Autowired
	private AficionRepository aficionRepository;
	
	
	@GetMapping("/aficion/r")
	public String r(ModelMap m , HttpSession s) throws DangerException {
		//Comprobar que solo pueden acceder los usuarios autenticados
		//H.isRolOK("auth", s);
		
		List<Aficion> aficiones = aficionRepository.findAll();
		m.put("aficiones", aficiones);
		
		//Desplegar vista con bootstrap
		m.put("view", "aficion/r");
		return "_t/frame";
	}
	
	
	@GetMapping("/aficion/c")
	public String c(ModelMap m) {
		m.put("view", "aficion/c");
		return "_t/frame";
	}
	
	
	@PostMapping("/aficion/cPost")
	public void cPost(
			@RequestParam("nombre")
			String nombre) throws DangerException, InfoException {
		
		try {
			aficionRepository.save(new Aficion(nombre));
		}
		catch (Exception e) {
			PRG.error("La aficion " + nombre + " ya existe", "/aficion/c");
		}
		
		PRG.info("Aficion " + nombre + " creada correctament", "/aficion/r");
	}
	
	
	//EDITAR
		@PostMapping("/aficion/u")
		public String u(ModelMap m,
					   @RequestParam("aficionId")Long id) {
			
			Aficion aficion = aficionRepository.getById(id);
			m.put("aficion", aficion);
			
			m.put("view", "aficion/u");
			return "_t/frame";
		}
		
		
		@PostMapping("/aficion/uPost")
		public void uPost(
				@RequestParam("nombre")String nombre,
				@RequestParam("idAficion")Long id) throws DangerException, InfoException {
		
			try {
				Aficion aficion = aficionRepository.getById(id);
				aficion.setNombre(nombre);
				aficionRepository.save(aficion);
			}
			catch (Exception e){
				PRG.error("La aficion " + nombre + " no se ha podido actualizar", "/aficion/r");
			}
		
			PRG.info("Aficion " + nombre + " editada correctamente", "/aficion/r");
		}
		
		
		//DELETE
				@PostMapping("/aficion/d")
				public void d(
						@RequestParam("borrarIdAficion")Long idAficion) throws DangerException, InfoException {
					
					try {
						aficionRepository.deleteById(idAficion);
					}
					catch (Exception e){
						PRG.error("La aficion no se ha podido eliminar" + e.getMessage(), "/aficion/r");
					}
				
					PRG.info("Aficion eliminada correctamente", "/aficion/r");
				}

}
