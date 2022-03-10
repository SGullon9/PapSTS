package org.gullon.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	@OneToMany(mappedBy = "trabaja", cascade = CascadeType.ALL)
	private Collection<Trabajador> trabajadores;

	
	public Empresa() {
		this.trabajadores = new ArrayList<Trabajador>();
	}

	public Empresa(String nombre) {
		this.nombre = nombre;
		this.trabajadores = new ArrayList<Trabajador>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Trabajador> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(Collection<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	} 
	
	

}
