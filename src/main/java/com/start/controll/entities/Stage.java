package com.start.controll.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotEmpty(message = "Nome da etapa não pode ser vazia")
  private String name;

  @Lob
  @NotEmpty(message = "Descrição da etapa não pode ser vazia")
  private String description;

  @OneToMany(mappedBy = "stage")
  @JsonIgnoreProperties({"stage", "groupList"})
  private List<Module> modules;

  public Stage(String name, String description) {
    this.name = name;
    this.description = description;
  }
}

