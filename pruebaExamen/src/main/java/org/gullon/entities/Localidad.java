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
public class Localidad {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	private String provincia;
	
	@OneToMany(mappedBy = "suLocalidad", cascade = CascadeType.ALL)
	private Collection<Cliente> residentes;
	
	
	public Localidad() {
		this.residentes = new ArrayList<Cliente>();
	}

	public Localidad(String nombre,String provincia) {
		this.nombre = nombre;
		this.provincia = provincia;
		this.residentes = new ArrayList<Cliente>();
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

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Collection<Cliente> getResidentes() {
		return residentes;
	}

	public void setResidentes(Collection<Cliente> residentes) {
		this.residentes = residentes;
	}
	
	
	

}
