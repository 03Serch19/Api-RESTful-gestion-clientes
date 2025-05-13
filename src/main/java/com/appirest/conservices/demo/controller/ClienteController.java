package com.appirest.conservices.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.appirest.conservices.demo.model.dao.ClienteDao;
import com.appirest.conservices.demo.model.dto.ClienteDto;
import com.appirest.conservices.demo.model.entity.Cliente;
import com.appirest.conservices.demo.model.payload.MensajeResponse;
import com.appirest.conservices.demo.service.impl.ClienteServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    //private final ClienteDao clienteDao;
  @Autowired
  private ClienteServiceImpl clienteService;

   /*  ClienteController(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }*/

  /*@PostMapping("cliente")
  @ResponseStatus(HttpStatus.CREATED)
  public ClienteDto create(@RequestBody ClienteDto clienteDto) {
    Cliente clienteCreate = clienteService.saveC(clienteDto);
    return ClienteDto.builder()
        .id(clienteCreate.getId())
        .nombre(clienteCreate.getNombre())
        .apellido(clienteCreate.getApellido())
        .email(clienteCreate.getEmail())
        .fechaRegistro(clienteCreate.getFechaRegistro())
        .build();
  }*/
  @PostMapping("cliente")
  public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
    Cliente clienteCreate=null;
    try {  
      if(clienteDto.getId()!=null){
      if(clienteService.existByIdC(clienteDto.getId())){
        return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("Ya existe el cliente con id: "+clienteDto.getId()+" El id no debe ser agregado para registros nuevos")
        .objeto(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
      }}
      clienteCreate = clienteService.saveC(clienteDto,-1);
      return new ResponseEntity<>(MensajeResponse.builder()
      .mensaje("Registro exitoso")
        .objeto(ClienteDto.builder()
        .id(clienteCreate.getId())
        .nombre(clienteCreate.getNombre())
        .apellido(clienteCreate.getApellido())
        .email(clienteCreate.getEmail())
        .fechaRegistro(clienteCreate.getFechaRegistro())
      .build())
      .build(), HttpStatus.CREATED);
    } catch (DataAccessException exDt) {
      // TODO: handle exception
      return new ResponseEntity<>(MensajeResponse.builder()
      .mensaje(exDt.getMessage())
      .objeto(null)
      .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
  }


  @PutMapping("cliente/{id}")
  public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto,@PathVariable Integer id) {
    Cliente clienteUpdate=null;
    try {
      if(!clienteService.existByIdC(id)){
        return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("No existe el cliente a actualizar con id: "+id)
        .objeto(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      /*if(!(clienteDto.getId().equals(id))){
        clienteDto.setId(id);
      }*/
      clienteUpdate = clienteService.saveC(clienteDto,id);
      return new ResponseEntity<>(MensajeResponse.builder()
      .mensaje("Actualizado correctamente")
        .objeto(ClienteDto.builder()
        .id(clienteUpdate.getId())
        .nombre(clienteUpdate.getNombre())
        .apellido(clienteUpdate.getApellido())
        .email(clienteUpdate.getEmail())
        .fechaRegistro(clienteUpdate.getFechaRegistro())
        .build())
      .build(), HttpStatus.CREATED);
    } catch (DataAccessException exDt) {
      // TODO: handle exception
      return new ResponseEntity<>(MensajeResponse.builder()
      .mensaje(exDt.getMessage())
      .objeto(null)
      .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  
  }
  
  //v1 manejo excepcion
  /*@DeleteMapping("cliente/{id}")
  //@ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    Map<String, Object> response = new HashMap<>(); 
    try {
      Cliente clienteDelete = clienteService.findByIdC(id);
      clienteService.deleteC(clienteDelete);
      return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);   
    } catch (DataAccessException exDt) {
         response.put("mensaje", exDt.getMessage());
         response.put("cliente", null);
          return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

    //v2 manejo excepcion
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
      try {
        Cliente clienteDelete = clienteService.findByIdC(id);
        clienteService.deleteC(clienteDelete);
        return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);   
      } catch (DataAccessException exDt) {
            return new ResponseEntity<>(MensajeResponse.builder()
            .mensaje(exDt.getMessage())
            .objeto(null)
            .build(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

  @GetMapping("cliente/{id}")
  public ResponseEntity<?> showById(@PathVariable Integer id) {
    Cliente cliente = clienteService.findByIdC(id);
    if(cliente==null){
       return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("No existe el cliente con id: "+id)
        .objeto(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
   return new ResponseEntity<>(MensajeResponse.builder()
   .mensaje("")
   .objeto(ClienteDto.builder()
        .id(cliente.getId())
        .nombre(cliente.getNombre())
        .apellido(cliente.getApellido())
        .email(cliente.getEmail())
        .fechaRegistro(cliente.getFechaRegistro())
        .build())
        .build(), HttpStatus.OK);
  }


  @GetMapping("clientes")
  public ResponseEntity<?> showAll() {
    List<Cliente> clientes = clienteService.findAllC();
    if(clientes==null){
      return new ResponseEntity<>(MensajeResponse.builder()
        .mensaje("Hubo un error al obtner clientes")
        .objeto(null)
        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
     if(clientes.isEmpty()){
    return new ResponseEntity<>(MensajeResponse.builder()
      .mensaje("No existen clientes registrados")
      .objeto(null)
      .build(), HttpStatus.OK);
  }
    List<ClienteDto> clientesDto = clientes.stream().map(cliente -> ClienteDto.builder()
        .id(cliente.getId())
        .nombre(cliente.getNombre())
        .apellido(cliente.getApellido())
        .email(cliente.getEmail())
        .fechaRegistro(cliente.getFechaRegistro())
        .build()).toList();

    return new ResponseEntity<>(MensajeResponse.builder()   
    .mensaje("")
    .objeto(clientesDto)
    .build(), HttpStatus.OK);
  }
}
