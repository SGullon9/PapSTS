package org.gullon.pap2022.entities;

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
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	//Poner el atributo que desde el otro lado (persona) apunta a pais
	@OneToMany(mappedBy = "nace", cascade = CascadeType.ALL)
	private Collection<Persona> nativos;
	
	//Borrar en cascada para que no de problema
	@OneToMany(mappedBy = "reside", cascade = CascadeType.ALL)
	private Collection<Persona> residentes;
	

	public Pais() {
		this.nombre = "Narnia";
		//Poner siempre una coleccion vacia en el constructor
		this.nativos = new ArrayList<Persona>();
		this.residentes = new ArrayList<Persona>();
	}
	
	//NUNCA PONER ATRIBUTO ID EN EL CONSTRUCTOR
	public Pais(String nombre) {
		this.nombre = nombre;
		this.nativos = new ArrayList<Persona>();
		this.residentes = new ArrayList<Persona>();
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

	public Collection<Persona> getNativos() {
		return nativos;
	}

	public void setNativos(Collection<Persona> nativos) {
		this.nativos = nativos;
	}

	public Collection<Persona> getResidentes() {
		return residentes;
	}

	public void setResidentes(Collection<Persona> residentes) {
		this.residentes = residentes;
	}

	
	
	
}
