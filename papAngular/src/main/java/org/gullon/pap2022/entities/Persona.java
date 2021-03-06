package org.gullon.pap2022.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	private String pwd;
	
	@ManyToOne
	private Pais nace;
	
	@ManyToOne
	private Pais reside;
	

	public Persona() {
		this.nombre = "basura";
	}

	public Persona(String nombre,String pwd,Pais nace,Pais reside) {
		this.nombre = nombre;
		this.pwd = encriptar(pwd);
		this.nace = nace;
		this.nace.getNativos().add(this);
		this.reside = reside;
		this.reside.getResidentes().add(this);
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = encriptar(pwd);
	} 


	private String encriptar(String pwd) {
		return (new BCryptPasswordEncoder().encode(pwd));
	}

	public Pais getNace() {
		return nace;
	}

	public void setNace(Pais nace) {
		this.nace = nace;
		this.nace.getNativos().add(this);
	}
	
	public Pais getReside() {
		return reside;
	}

	public void setReside(Pais reside) {
		this.reside = reside;
		this.reside.getResidentes().add(this);
	}

	

////////////////////////////////////////////////////////////////////////////////////////////////////////

	

}
