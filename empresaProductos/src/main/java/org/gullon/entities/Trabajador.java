package org.gullon.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Trabajador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	private String pwd;
	
	@ManyToOne
	private Empresa trabaja;
	
	@ManyToMany
	private Collection<Productos> productosHechos;

	
	public Trabajador() {
		this.nombre = "basura";
		this.productosHechos = new ArrayList<Productos>();
	}

	public Trabajador(String nombre, String pwd, Empresa trabaja) {
		super();
		this.nombre = nombre;
		this.pwd = pwd;
		this.trabaja = trabaja;
		this.trabaja.getTrabajadores().add(this);
		this.productosHechos = new ArrayList<Productos>();
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
		this.pwd = pwd;
	}

	public Empresa getTrabaja() {
		return trabaja;
	}

	public void setTrabaja(Empresa trabaja) {
		this.trabaja = trabaja;
	}

	
	public Collection<Productos> getProductosHechos() {
		return productosHechos;
	}

	public void setProductosHechos(Collection<Productos> productosHechos) {
		this.productosHechos = productosHechos;
	}
	
	public void addAficionGusta(Productos productos) {
		this.productosHechos.add(productos);
		productos.getTrabajadoresQueMeHanHecho().add(this);
	}

	//-----------------------------------------------------
	public String getRol() {
		return nombre.equals("admin")? "admin":"auth";
	}
	
	
	

}
