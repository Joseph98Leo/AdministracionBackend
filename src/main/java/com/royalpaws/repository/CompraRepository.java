package com.royalpaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.royalpaws.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer>{

}
