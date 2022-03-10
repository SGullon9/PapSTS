package org.gullon.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.gullon.entities.Productos;
import org.gullon.exception.DangerException;
import org.gullon.exception.InfoException;
import org.gullon.exception.PRG;
import org.gullon.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productos")
public class ProductosController {
	
	
	@Autowired
	private ProductosRepository productosRepository;
	
	
	@GetMapping("r")
	public String r(ModelMap m , HttpSession s) throws DangerException {
		
		List<Productos> productos = productosRepository.findAll();
		m.put("productos", productos);
		
		m.put("view", "productos/r");
		return "_t/frame";
	}
	
	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "productos/c");
		return "_t/frame";
	}
	
	
	@PostMapping("cPost")
	public void cPost(
			@RequestParam("nombre")String nombre,
			@RequestParam("tipos")String tipos) throws DangerException, InfoException {
		
		try {
			productosRepository.save(new Productos(nombre,tipos));
		}
		catch (Exception e) {
			PRG.error("El producto " + nombre + " ya existe", "/productos/r");
		}
		
		PRG.info("Producto " + nombre + " creada correctament", "/productos/r");
	}
	

}
