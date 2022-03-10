package org.gullon.controller;

import java.util.ArrayList;
import java.util.List;

import org.gullon.entities.Empresa;
import org.gullon.entities.Productos;
import org.gullon.entities.Trabajador;
import org.gullon.exception.DangerException;
import org.gullon.exception.InfoException;
import org.gullon.exception.PRG;
import org.gullon.repository.EmpresaRepository;
import org.gullon.repository.ProductosRepository;
import org.gullon.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trabajador")
public class TrabajadorController {
	
	@Autowired
	private TrabajadorRepository trabajadorRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ProductosRepository productosRepository;
	
	@GetMapping("r") 
	public String r(ModelMap m) {
		
		List<Trabajador> trabajadores = trabajadorRepository.findAll();
		m.put("trabajadores", trabajadores); 
		
		
		m.put("view", "trabajador/r");
		return "_t/frame";
	}
	
	
	@GetMapping("c")
	public String c(ModelMap m) {
		
		m.put("empresas", empresaRepository.findAll());
		m.put("productos", productosRepository.findAll());
		 
		m.put("view", "trabajador/c");
		return "_t/frame";
	}
	
	
	@PostMapping("cPost")
	public void cPost(
			@RequestParam("nombre") String nombre, 
			@RequestParam("pwd") String pwd,
			@RequestParam("IdEmpresaTrabaja") Long IdEmpresaTrabaja,
			@RequestParam(value="idsProductos[]",required=false) List<Long> idsProductos)throws DangerException, InfoException {
		
		try {
		Trabajador trabajador = new Trabajador(nombre,pwd,empresaRepository.getById(IdEmpresaTrabaja));
		
		if(idsProductos != null) {
		  for(Long idProducto : idsProductos) {
			  trabajador.addAficionGusta(productosRepository.getById(idProducto));
		  }
		}
		
		trabajadorRepository.save(trabajador);

		}
		catch (Exception e){
			PRG.error("ERROR: " + e.getMessage(), "/trabajador/c");
		}
		
		PRG.info("Trabajador " + nombre + " creado correctamente", "/trabajador/r");
	}
	
	
	
	//EDITAR
		@PostMapping("u")
		public String u(ModelMap m,
				@RequestParam("idTrabajador")Long idTrabajador) {
			
			m.put("empresas", empresaRepository.findAll());
			m.put("productos", productosRepository.findAll());
			m.put("trabajador", trabajadorRepository.getById(idTrabajador));
			 
			m.put("view", "trabajador/u");
			return "_t/frame";
		}
		
		
		@PostMapping("uPost")
		public void uPost(
				@RequestParam("idTrabajador") Long idTrabajador,
				@RequestParam("nombre") String nombre,
				@RequestParam("IdEmpresa") Long IdEmpresa,
				@RequestParam(value="idsProductos[]",required=false) List<Long> idsProductos)throws DangerException, InfoException {
			
			try {
			Trabajador trabajador = trabajadorRepository.getById(idTrabajador);
			trabajador.setNombre(nombre);
			
			if (IdEmpresa != trabajador.getTrabaja().getId()) {
				Empresa nuevaEmpresa = empresaRepository.getById(IdEmpresa);
				trabajador.setTrabaja(nuevaEmpresa);
			}
			
			ArrayList<Productos> nuevasProductos = new ArrayList<Productos>();
			if(idsProductos != null) {
			  for(Long idProducto : idsProductos) {
				  nuevasProductos.add(productosRepository.getById(idProducto));
			  }
			}
			trabajador.setProductosHechos(nuevasProductos);
				
			
			trabajadorRepository.save(trabajador);
			
			}
			catch (Exception e){
				PRG.error("El trabajador " + nombre + " no se ha podido actualizar", "/trabajador/r");
			}
			
			PRG.info("Trabajador " + nombre + " editado correctamente", "/trabajador/r");
		}
		
		
		
		//DELETE
			@PostMapping("d")
			public void d(
					@RequestParam("borrarIdTrabajador")Long idTrabajador) throws DangerException, InfoException {
					
				try {
					trabajadorRepository.deleteById(idTrabajador);
				}
				catch (Exception e){
					PRG.error("El trabajador no se ha podido eliminar" + e.getMessage(), "/trabajador/r");
				}
				
				PRG.info("Trabajador eliminado correctamente", "/trabajador/r");
			}

}
