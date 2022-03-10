package org.gullon.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Productos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	private String tipo;
	
	@ManyToMany(mappedBy = "productosHechos")
	private Collection<Trabajador> trabajadoresQueMeHanHecho;

	
	public Productos() {
		this.trabajadoresQueMeHanHecho = new ArrayList<Trabajador>();
	}

	public Productos(String nombre, String tipo) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.trabajadoresQueMeHanHecho = new ArrayList<Trabajador>();
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Collection<Trabajador> getTrabajadoresQueMeHanHecho() {
		return trabajadoresQueMeHanHecho;
	}

	public void setTrabajadoresQueMeHanHecho(Collection<Trabajador> trabajadoresQueMeHanHecho) {
		this.trabajadoresQueMeHanHecho = trabajadoresQueMeHanHecho;
	}
	
	
	
	

}
