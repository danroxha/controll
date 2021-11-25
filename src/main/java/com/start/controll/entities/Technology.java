package com.start.controll.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Technology {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotEmpty(message = "Nome da tecnologia não pode ser vazia")
  private String nome;

  @Column(nullable = false)
  @Lob
  @NotEmpty(message = "Descrição da tecnologia não pode ser vazia")
  private String descricao;

  public Technology(String nome, String descricao) {
    this.nome = nome;
    this.descricao = descricao;
  }
}

