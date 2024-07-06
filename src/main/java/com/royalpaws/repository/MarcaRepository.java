package com.royalpaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{

}
