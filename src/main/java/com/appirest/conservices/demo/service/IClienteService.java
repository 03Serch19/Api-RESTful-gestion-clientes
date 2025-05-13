package com.appirest.conservices.demo.service;

import java.util.List;

import com.appirest.conservices.demo.model.dto.ClienteDto;
import com.appirest.conservices.demo.model.entity.Cliente;

public interface IClienteService {
  List<Cliente> findAllC();
  Cliente saveC(ClienteDto clienteDto,Integer id);
  Cliente findByIdC(Integer id);
  void deleteC(Cliente cliente);
  boolean existByIdC(Integer id);
}
