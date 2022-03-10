package org.gullon.pap2022.controller;

import javax.servlet.http.HttpSession;

import org.gullon.pap2022.entities.Persona;
import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.helper.H;
import org.gullon.pap2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {
	
	@Autowired
	private PersonaRepository personaRepository; 
	
	
	//MENSAJE DE ERROR
	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {

		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}

	
	//LOGIN
	@GetMapping("/login")
	public String login(ModelMap m, HttpSession s) throws DangerException {
		//ROLES
		//H.isRolOK("anon", s); 
		m.put("view", "home/login");
		return "_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("pwd") String pwd,
			HttpSession s) throws InfoException {
		
		String returnLocation="redirect:/";
		try {
		//Buscar por un atributo dentro de la BD
		Persona persona = personaRepository.getByNombre(nombre);
		//Comprobar que la pwd corresponde con la persona insertada
		if (new BCryptPasswordEncoder().matches(pwd, persona.getPwd())) {
			//Guarde todo lo de la persona en una cookie para poder sacar cualquier dato cuando lo necesite por getId...
			s.setAttribute("persona", persona);
		  }
		else {
			returnLocation = "redirect:/errorDisplay?msg=Password incorrecta";
		 }
		}
		catch (Exception e) {
			returnLocation = "redirect:/errorDisplay?msg=Usuario incorrecto";
		}
		
		return returnLocation;	
	}
	
	
	@GetMapping("/logout")
	public String logout(
			HttpSession s){
		
		//Borrar variable de sesion
		s.invalidate();
		
		return "redirect:/";
	}
	
	
	//HOME
	@GetMapping("/")
	public String index(ModelMap m) {
		m.put("view", "home/index2.html");
		return "_t/frame";
	}
}