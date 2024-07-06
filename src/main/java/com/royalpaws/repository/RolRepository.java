package com.royalpaws.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

	//Para buscar un rol por su nombre en BD
    Optional<Rol> findByNomRol(String name);
}
