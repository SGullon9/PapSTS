package org.gullon.pap2022.repository;

import org.gullon.pap2022.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{
	
	//Para sacar a las perosnas por el nombre
	public Persona getByNombre(String nombre);
}
