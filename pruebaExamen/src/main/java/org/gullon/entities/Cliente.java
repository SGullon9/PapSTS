package org.gullon.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	 
	@ManyToOne
	private Localidad suLocalidad;

	
	public Cliente() {
	}
	
	public Cliente(String nombre,Localidad suLocalidad) {
		super();
		this.nombre = nombre;
		this.suLocalidad = suLocalidad;
		this.suLocalidad.getResidentes().add(this);
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

	public Localidad getSuLocalidad() {
		return suLocalidad;
	}

	public void setSuLocalidad(Localidad suLocalidad) {
		this.suLocalidad = suLocalidad;
	}

	
	

}
