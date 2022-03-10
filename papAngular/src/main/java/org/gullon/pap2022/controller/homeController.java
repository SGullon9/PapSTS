package org.gullon.pap2022.controller;

import javax.servlet.http.HttpSession;


import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.helper.H;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {
	
	
	
	
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

	
	
	
	//HOME
	@GetMapping("/")
	public String index(ModelMap m) {
		m.put("view", "home/index2.html");
		return "_t/frame";
	}
}