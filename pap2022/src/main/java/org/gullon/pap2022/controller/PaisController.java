package org.gullon.pap2022.controller;

import java.util.List;

import org.gullon.pap2022.entities.Pais;
import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.repository.PaisRepository;
import org.gullon.pap2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//PONER ESTO SI SE QUE TODAS LAS RUTAS SON DE /pais/
@RequestMapping("/pais")
public class PaisController {
	
	//Crear el repositorio para que se pueda acceder siempre que se quieran pasar datos
	@Autowired
	private PaisRepository paisRepository;
	
	
	@GetMapping("r")
	public String r(
			//Para pasar a la vista crear modelmap para poder acceder a las listas de cualquier bean
			ModelMap m
			) {
		
		List<Pais> paises = paisRepository.findAll();
		m.put("paises", paises);
		//DELETE
		//paisRepository.deleteById(idPais);
		
		m.put("view", "pais/r");
		return "_t/frame";
	}
	
	
	//CREAR 
	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "pais/c");
		return "_t/frame";
	}
	
	
	@PostMapping("cPost")
	//Por el parametro recoge los datos por el name
	public void cPost(
			@RequestParam("nombre")String nombre) throws DangerException, InfoException {
	
		try {
			paisRepository.save(new Pais(nombre));
		}
		catch (Exception e){
			PRG.error("El pais " + nombre + " ya existe", "/pais/c");
		}
		
		//La excepcion tipo info siempre fuera del try/catch
		PRG.info("Pais " + nombre + " creado correctamente", "/pais/r");
	}
	
	
	//EDITAR
	@PostMapping("u")
	public String u(ModelMap m,
				   @RequestParam("paisId")Long id) {
		
		Pais pais = paisRepository.getById(id);
		m.put("pais", pais);
		
		m.put("view", "pais/u");
		return "_t/frame";
	}
	
	@PostMapping("uPost")
	public void uPost(
			@RequestParam("nombre")String nombre,
			@RequestParam("idPais")Long id) throws DangerException, InfoException {
	
		try {
			Pais pais = paisRepository.getById(id);
			pais.setNombre(nombre);
			paisRepository.save(pais);
		}
		catch (Exception e){
			PRG.error("El pais " + nombre + " no se ha podido actualizar", "/pais/r");
		}
	
		PRG.info("Pais " + nombre + " editado correctamente", "/pais/r");
	}
	
	
	//DELETE
	@PostMapping("d")
	public void d(
			@RequestParam("borrarIdPais")Long idPais) throws DangerException, InfoException {
		
		try {
			paisRepository.deleteById(idPais);
		}
		catch (Exception e){
			PRG.error("El pais no se ha podido eliminar" + e.getMessage(), "/pais/r");
		}
	
		PRG.info("Pais eliminado correctamente", "/pais/r");
	}
	
}