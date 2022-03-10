package org.gullon.controller;

import java.util.List;

import org.gullon.entities.Empresa;
import org.gullon.exception.DangerException;
import org.gullon.exception.InfoException;
import org.gullon.exception.PRG;
import org.gullon.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("r")
	public String r(ModelMap m) {
		
		List<Empresa> empresas = empresaRepository.findAll();
		m.put("empresas", empresas);
		//DELETE
		//paisRepository.deleteById(idPais);
		
		m.put("view", "empresa/r");
		return "_t/frame";
	}
	
	
	//CREAR 
		@GetMapping("c")
		public String c(ModelMap m) {
			m.put("view", "empresa/c");
			return "_t/frame";
		}
		
		
		@PostMapping("cPost")
		public void cPost(
				@RequestParam("nombre")String nombre) throws DangerException, InfoException {
		
			try {
				empresaRepository.save(new Empresa(nombre));
			}
			catch (Exception e){
				PRG.error("La empresa " + nombre + " ya existe", "/empresa/r");
			}
			
			PRG.info("Empresa " + nombre + " creado correctamente", "/empresa/r");
		}
	
	

}
