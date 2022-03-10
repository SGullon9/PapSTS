package org.gullon.pap2022.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Aficion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	@ManyToMany(mappedBy = "aficionesGusta")
	private Collection<Persona> personasGustan;
	
	@ManyToMany(mappedBy = "aficionesOdia")
	private Collection<Persona> personasOdian;


	public Aficion() {
		this.nombre = "narnia";
		this.personasGustan = new ArrayList<Persona>();
		this.personasOdian = new ArrayList<Persona>();
	}

	public Aficion(String nombre) {
		this.nombre = nombre;
		this.personasGustan = new ArrayList<Persona>();
		this.personasOdian = new ArrayList<Persona>();
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

	public Collection<Persona> getPersonasGustan() {
		return personasGustan;
	}

	public void setPersonasGustan(Collection<Persona> personasGustan) {
		this.personasGustan = personasGustan;
	}

	public Collection<Persona> getPersonasOdian() {
		return personasOdian;
	}

	public void setPersonasOdian(Collection<Persona> personasOdian) {
		this.personasOdian = personasOdian;
	}
	

}
