package com.royalpaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royalpaws.model.Cliente;
import com.royalpaws.repository.ClienteRepository;

@Service
public class ClienteService {
	
		@Autowired
		private ClienteRepository clienteRepo;
		
		public List<Cliente> listarClientes() {
			return clienteRepo.findAll();
		}
		
	    public Cliente grabar(Cliente cliente) {
	        return clienteRepo.save(cliente);
	    }

		public Cliente buscarPorId(Integer cod) {
			return clienteRepo.findById(cod).orElse(null);
		}

		public void eliminar(Integer cod) {
			clienteRepo.deleteById(cod);
		}

		public List<Cliente> listarClienteXApellido(String ape){
			return clienteRepo.listApellidoCliente(ape);
		}
					
}
