package com.appirest.conservices.demo.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.appirest.conservices.demo.model.entity.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Integer> {
  // No additional methods are needed as the CrudRepository provides basic CRUD operations.

}
