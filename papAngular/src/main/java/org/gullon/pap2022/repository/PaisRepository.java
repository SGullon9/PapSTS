package org.gullon.pap2022.repository;

import org.gullon.pap2022.entities.Pais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends CrudRepository<Pais, Long>{
	 
}
