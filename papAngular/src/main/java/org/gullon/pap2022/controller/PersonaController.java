package org.gullon.pap2022.controller;

import java.util.List;

import org.gullon.pap2022.entities.Persona;
import org.gullon.pap2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	
	@GetMapping("/personas")
    public List<Persona> getUsers() {
        return (List<Persona>) personaRepository.findAll();
    }
	
	@PostMapping("/personas")
    void addUser(@RequestBody Persona persona) {
		personaRepository.save(persona);
    }
	
	
}
