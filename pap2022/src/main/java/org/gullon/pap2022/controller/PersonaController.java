package org.gullon.pap2022.controller;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.gullon.pap2022.entities.Aficion;
import org.gullon.pap2022.entities.Pais;
import org.gullon.pap2022.entities.Persona;
import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.repository.AficionRepository;
import org.gullon.pap2022.repository.PaisRepository;
import org.gullon.pap2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PersonaController {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private AficionRepository aficionRepository;	
	
	@Value("${app.uploadFolder}")
	private String UPLOADED_FOLDER; 
	
	
	@GetMapping("/persona/r")
	public String r(
			ModelMap m 
			) {
		
		List<Persona> personas = personaRepository.findAll();
		m.put("personas", personas);
		
		
		m.put("view", "persona/r");
		return "_t/frame";
	}
	
	
	@GetMapping("/persona/c")
	public String c(ModelMap m) {
		
		//Pasar la lista de paises/aficiones para poder llevartelas a la vista
		m.put("paises", paisRepository.findAll());
		m.put("aficiones", aficionRepository.findAll());
		 
		m.put("view", "persona/c");
		return "_t/frame";
	}
	
	
	@PostMapping("/persona/cPost")
	public void cPost(
			@RequestParam("nombre") String nombre, 
			@RequestParam("pwd") String pwd,
			//@RequestParam("foto") MultipartFile foto,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,  
			@RequestParam("IdPaisNace") Long IdPaisNace,
			@RequestParam("IdPaisReside") Long idReside,
			//Para que se puedan insertar tambien nulos
			@RequestParam(value="idsAficion[]",required=false) List<Long> idsAficiones,
			@RequestParam(value="idsAficionOdia[]",required=false) List<Long> idsAficionesOdia)throws DangerException, InfoException {
		
		try {
		Persona persona = new Persona(nombre,pwd,fecha,paisRepository.getById(IdPaisNace),paisRepository.getById(idReside));
		//Insertar cada una de las aficiones que le gustan recorriendo todas las que llegan
		if(idsAficiones != null) {
		  for(Long idAficion : idsAficiones) {
			  persona.addAficionGusta(aficionRepository.getById(idAficion));
		  }
		}
		
		if(idsAficionesOdia != null) {
			  for(Long idAficionO : idsAficionesOdia) {
				  persona.addAficionOdia(aficionRepository.getById(idAficionO));
			  }
			}
		
		//SUBIR FOTO
		//try { 
		//	byte[] bytes = foto.getBytes();
		//	Path path = Paths.get(UPLOADED_FOLDER + foto.getOriginalFilename());
		//	Files.write(path, bytes);
		//}

		//catch (Exception e) {
		//	PRG.error(e.getMessage());
		//}
			
		personaRepository.save(persona);

		}
		catch (Exception e){
			PRG.error("ERROR: " + e.getMessage(), "/persona/c");
		}
		
		PRG.info("Persona " + nombre + " creada correctamente", "/persona/r");
	}
	
	
	//EDITAR
	@PostMapping("/persona/u")
	public String u(ModelMap m,
			@RequestParam("idPersona")Long idPersona) {
		
		m.put("paises", paisRepository.findAll());
		m.put("aficiones", aficionRepository.findAll());
		m.put("persona", personaRepository.getById(idPersona));
		 
		m.put("view", "persona/u");
		return "_t/frame";
	}
	
	
	@PostMapping("/persona/uPost")
	public void uPost(
			@RequestParam("idPersona") Long idPersona,
			@RequestParam("nombre") String nombre,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			@RequestParam("IdPaisNace") Long IdPaisNace,
			@RequestParam("IdPaisReside") Long IdPaisReside,
			@RequestParam(value="idsAficion[]",required=false) List<Long> idsAficiones,
			@RequestParam(value="idsAficionOdia[]",required=false) List<Long> idsAficionesOdia)throws DangerException, InfoException {
		
		try {
		Persona persona = personaRepository.getById(idPersona);
		persona.setNombre(nombre);
		
		persona.setFecha(fecha);
		
		if (IdPaisNace != persona.getNace().getId()) {
			Pais nuevoPaisNacimiento = paisRepository.getById(IdPaisNace);
			persona.setNace(nuevoPaisNacimiento);
		}
		
		if (IdPaisReside != persona.getReside().getId()) {
			Pais nuevoPaisResidencia = paisRepository.getById(IdPaisReside);
			persona.setReside(nuevoPaisResidencia);
		}
		
		ArrayList<Aficion> nuevasAficiones = new ArrayList<Aficion>();
		if(idsAficiones != null) {
		  for(Long idAficion : idsAficiones) {
			  //Sobre el conjunto de aficiones nuevas la sustituyes por la antigua
			  nuevasAficiones.add(aficionRepository.getById(idAficion));
		  }
		}
		persona.setAficionesGusta(nuevasAficiones);
		
		ArrayList<Aficion> nuevasAficionesO = new ArrayList<Aficion>();
		if(idsAficionesOdia != null) {
		  for(Long idAficionO : idsAficionesOdia) {
			  nuevasAficionesO.add(aficionRepository.getById(idAficionO));
		  }
		}
		persona.setAficionesOdia(nuevasAficionesO);
		
		
		personaRepository.save(persona);
		}
		catch (Exception e){
			PRG.error("La persona " + nombre + " no se ha podido actualizar", "/persona/r");
		}
		
		PRG.info("Persona " + nombre + " editada correctamente", "/persona/r");
	}
	
	
	//DELETE
		@PostMapping("/persona/d")
		public void d(
				@RequestParam("borrarIdPersona")Long idPersona) throws DangerException, InfoException {
			
			try {
				personaRepository.deleteById(idPersona);
			}
			catch (Exception e){
				PRG.error("La persona no se ha podido eliminar" + e.getMessage(), "/persona/r");
			}
		
			PRG.info("Persona eliminado correctamente", "/persona/r");
		}

}
