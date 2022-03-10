package org.gullon.pap2022.entities;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Hacerlo unico para que no de problemas con el login
	@Column(unique = true)
	private String nombre;
	
	//LOGIN
	private String pwd;
	
	private LocalDate fecha;
	
	@ManyToOne
	private Pais nace;
	
	@ManyToOne
	private Pais reside;
	
	@ManyToMany
	private Collection<Aficion> aficionesGusta;
	
	@ManyToMany
	private Collection<Aficion> aficionesOdia;

	public Persona() {
		this.nombre = "basura";
		this.aficionesGusta = new ArrayList<Aficion>();
	}

	public Persona(String nombre,String pwd,LocalDate fecha,Pais nace,Pais reside) {
		this.nombre = nombre;
		this.pwd = encriptar(pwd);
		this.fecha = fecha;
		this.nace = nace;
		this.nace.getNativos().add(this);
		this.reside = reside;
		this.reside.getResidentes().add(this);
		this.aficionesGusta = new ArrayList<Aficion>();
		this.aficionesOdia = new ArrayList<Aficion>();
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	//Guardarlo encriptado para el login
	private String encriptar(String pwd) {
		return (new BCryptPasswordEncoder().encode(pwd));
	}

	public Pais getNace() {
		return nace;
	}

	public void setNace(Pais nace) {
		this.nace = nace;
		//Añadir todos los nativos de pais
		this.nace.getNativos().add(this);
	}
	
	public Pais getReside() {
		return reside;
	}

	public void setReside(Pais reside) {
		this.reside = reside;
		this.reside.getResidentes().add(this);
	}

	//AFICIONES QUE GUSTAN
	public Collection<Aficion> getAficionesGusta() {
		return aficionesGusta;
	}

	public void setAficionesGusta(Collection<Aficion> aficionesGusta) {
		this.aficionesGusta = aficionesGusta;
	}
	
	    //ADD AFICIONES 
	public void addAficionGusta(Aficion aficion) {
		this.aficionesGusta.add(aficion);
		aficion.getPersonasGustan().add(this);
	}

	//AFICIONES QUE ODIA
	public Collection<Aficion> getAficionesOdia() {
		return aficionesOdia;
	}

	public void setAficionesOdia(Collection<Aficion> aficionesOdia) {
		this.aficionesOdia = aficionesOdia;
	}
	
	public void addAficionOdia(Aficion aficion) {
		this.aficionesOdia.add(aficion);
		aficion.getPersonasOdian().add(this);
	}
	

////////////////////////////////////////////////////////////////////////////////////////////////////////

	//EDAD 
	public Integer getEdad() {
		int sol = 0;
		LocalDate fnac = this.getFecha();
		if(fnac != null) {
			LocalDate hoy = LocalDate.now();
			Period intervalo = Period.between(fnac, hoy);
			sol = intervalo.getYears();
		}
		return sol;
	}

	
	public String getRol() {
		return nombre.equals("admin")? "admin":"auth";
	}

}
