package com.appirest.conservices.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@ToString
@Builder
public class ClienteDto implements Serializable {

  private Integer id;
  private String nombre;
  private String apellido;
  private String email;
  private Date fechaRegistro;
}