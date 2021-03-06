package org.gullon.pap2022.controller;

import java.util.List;


import org.gullon.pap2022.entities.Pais;
import org.gullon.pap2022.exception.DangerException;
import org.gullon.pap2022.exception.InfoException;
import org.gullon.pap2022.exception.PRG;
import org.gullon.pap2022.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaisController {
	
	@Autowired
	private PaisRepository paisRepository;
	
	
	@GetMapping("/paises")
    public List<Pais> getUsers() {
        return (List<Pais>) paisRepository.findAll();
    }
	
	@PostMapping("/paises")
    void addUser(@RequestBody Pais pais) {
        paisRepository.save(pais);
    }
	
}