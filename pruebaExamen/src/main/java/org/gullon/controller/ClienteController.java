package org.gullon.controller;

import java.util.List;

import org.gullon.entities.Cliente;
import org.gullon.entities.Localidad;
import org.gullon.exception.DangerException;
import org.gullon.exception.InfoException;
import org.gullon.exception.PRG;
import org.gullon.repository.ClienteRepository;
import org.gullon.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private LocalidadRepository localidadRepository;
	
	@GetMapping("r") 
	public String r(ModelMap m) {
		
		List<Cliente> clientes = clienteRepository.findAll();
		m.put("clientes", clientes);
		List<Localidad> localidades = localidadRepository.findAll();
		m.put("localidades", localidades);
		
		m.put("view", "cliente/r");
		return "_t/frame";
	}
	
	@GetMapping("c")
	public String c(ModelMap m) {
		
		m.put("localidades", localidadRepository.findAll());
		 
		m.put("view", "cliente/c");
		return "_t/frame";
	}
	
	@PostMapping("cPost")
	public void cPost(
			@RequestParam("nombre") String nombre,  
			@RequestParam("IdLocalidad") Long IdLocalidad)throws DangerException, InfoException {
		
		try {
		Cliente cliente = new Cliente(nombre,localidadRepository.getById(IdLocalidad));
			
		clienteRepository.save(cliente);

		}
		catch (Exception e){
			PRG.error("ERROR: " + e.getMessage(), "/cliente/r");
		}
		
		PRG.info("Persona " + nombre + " creada correctamente", "/cliente/r");
	}
	 
	//EDITAR
	@PostMapping("u")
	public String u(ModelMap m,
		   @RequestParam("idCliente")Long id) {
			
		Cliente cliente = clienteRepository.getById(id);
		m.put("cliente", cliente);
		m.put("localidades", localidadRepository.findAll());
			
		m.put("view", "cliente/u");
		return "_t/frame";
		}
		
	
	@PostMapping("uPost")
	public void uPost(
			@RequestParam("idCliente") Long idCliente,
			@RequestParam("nombre") String nombre,
			@RequestParam("IdLocalidad") Long IdLocalidad)throws DangerException, InfoException {
		
		try {
		Cliente cliente = clienteRepository.getById(idCliente);
		cliente.setNombre(nombre);
		
		if (IdLocalidad != cliente.getSuLocalidad().getId()) {
			Localidad nuevaLocalidad = localidadRepository.getById(IdLocalidad);
			cliente.setSuLocalidad(nuevaLocalidad);
		}
		
		clienteRepository.save(cliente);
		}
		catch (Exception e){
			PRG.error("El cliente " + nombre + " no se ha podido actualizar", "/cliente/r");
		}
		
		PRG.info("Cliente " + nombre + " editado correctamente", "/cliente/r");
	}
	
	
	//DELETE
		@PostMapping("d")
		public void d(
			@RequestParam("borrarIdCliente")Long idCliente) throws DangerException, InfoException {
				
			try {
				clienteRepository.deleteById(idCliente);
			}
			catch (Exception e){
				PRG.error("El cliente no se ha podido eliminar" + e.getMessage(), "/cliente/r");
			}
			
			PRG.info("Cliente eliminado correctamente", "/cliente/r");
		}
	
	
}
