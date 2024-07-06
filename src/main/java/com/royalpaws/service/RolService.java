package com.royalpaws.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.royalpaws.model.Rol;
import com.royalpaws.repository.RolRepository;


@Service
public class RolService {

	@Autowired
	private RolRepository rolRepo;
	
	public List<Rol> listarRoles() {
		return rolRepo.findAll();
	}

	public Optional<Rol> buscarRolPorCodigo(Integer codigo) {
		return rolRepo.findById(codigo);
	}

	public Rol guardarRol(Rol rol) {
		return rolRepo.save(rol);
	}
	
	public void eliminarRol(Integer codigo) {
		rolRepo.deleteById(codigo);
	}
	
}
