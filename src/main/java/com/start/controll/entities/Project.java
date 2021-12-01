package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 255, message = "Nome muito longo")
  private String name;

  @Digits(fraction = 2, integer = 19)
  BigDecimal rating;

  @Lob
  private String  description;

  @Lob
  private String note;

  @Lob
  private String repository;

  @ManyToOne
  @JsonIgnoreProperties("projects")
  private Starter starter;

  @ManyToOne
  private Module module;

}
