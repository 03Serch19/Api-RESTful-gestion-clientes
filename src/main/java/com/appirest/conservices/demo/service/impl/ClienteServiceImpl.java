package com.appirest.conservices.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appirest.conservices.demo.model.dao.ClienteDao;
import com.appirest.conservices.demo.model.dto.ClienteDto;
import com.appirest.conservices.demo.model.entity.Cliente;
import com.appirest.conservices.demo.service.IClienteService;
@Service
public class ClienteServiceImpl implements IClienteService {
  @Autowired
  private ClienteDao clienteDao; 
    // Implement the methods from ICliente interface here
    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAllC() {
        // Implementation for finding all Clientes
    return (List) clienteDao.findAll();
     /*  List<Cliente> clientes = new ArrayList<>();
      clienteDao.findAll().forEach(clientes::add);
       return clientes;*/
       
    }
    @Transactional
    @Override
    public Cliente saveC(ClienteDto clienteDto,Integer id) {
        // Implementation for saving a Cliente entity
        Cliente cliente = Cliente.builder()
                .id((id==-1)?clienteDto.getId():id)
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .email(clienteDto.getEmail())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return clienteDao.save(cliente);// Replace with actual implementation

    }
    
    @Transactional(readOnly = true)
    @Override
    public Cliente findByIdC(Integer id) {
      // Implementation for finding a Cliente by ID
      return clienteDao.findById(id).orElse(null); // Replace with actual implementation
    }
    
    @Transactional
    @Override
    public void deleteC(Cliente cliente) {
        // Implementation for deleting a Cliente by ID
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existByIdC(Integer id) {
        // Implementation for checking if a Cliente exists by ID
        return clienteDao.existsById(id); // Replace with actual implementation
    }
   
}
