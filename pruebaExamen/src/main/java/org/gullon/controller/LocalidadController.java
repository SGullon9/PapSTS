package org.gullon.controller;

import java.util.List;


import org.gullon.entities.Localidad;
import org.gullon.exception.DangerException;
import org.gullon.exception.InfoException;
import org.gullon.exception.PRG;
import org.gullon.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {
	
	@Autowired
	private LocalidadRepository localidadRepository;
	
	@GetMapping("r")
	public String r(ModelMap m) {
		
		List<Localidad> localidades = localidadRepository.findAll();
		m.put("localidades", localidades);
		//DELETE
		//paisRepository.deleteById(idPais);
		
		m.put("view", "localidad/r");
		return "_t/frame";
	}
	
	//CREAR 
	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "localidad/c");
		return "_t/frame";
	}
	
	
	@PostMapping("cPost")
	public void cPost(
			@RequestParam("nombre")String nombre,
			@RequestParam("provincias")String provincia) throws DangerException, InfoException {
	
		try {
			localidadRepository.save(new Localidad(nombre,provincia));
		}
		catch (Exception e){
			PRG.error("La localidad " + nombre + " ya existe", "/localidad/r");
		}
		
		PRG.info("Localidad " + nombre + " creado correctamente", "/localidad/r");
	}
	
	
	//EDITAR
	@PostMapping("u")
	public String u(ModelMap m,
				   @RequestParam("localidadId")Long id) {
		
		Localidad localidad = localidadRepository.getById(id);
		m.put("localidad", localidad);
		
		m.put("view", "localidad/u");
		return "_t/frame";
	}
	
	@PostMapping("uPost")
	public void uPost(
			@RequestParam("nombre")String nombre,
			@RequestParam("provincias")String provincias,
			@RequestParam("idLocalidad")Long id) throws DangerException, InfoException {
	
		try {
			Localidad localidad = localidadRepository.getById(id);
			localidad.setNombre(nombre);
			localidad.setProvincia(provincias);
			localidadRepository.save(localidad);
		}
		catch (Exception e){
			PRG.error("La localidad " + nombre + " no se ha podido actualizar", "/localidad/r");
		}
	
		PRG.info("Localidad " + nombre + " editado correctamente", "/localidad/r");
	}
	
	

	//DELETE
	@PostMapping("d")
	public void d(
			@RequestParam("borrarIdLocalidad")Long idLocalidad) throws DangerException, InfoException {
		
		try {
			localidadRepository.deleteById(idLocalidad);
		}
		catch (Exception e){
			PRG.error("La localidad no se ha podido eliminar" + e.getMessage(), "/localidad/r");
		}
	
		PRG.info("Localidad eliminada correctamente", "/localidad/r");
	}

}
