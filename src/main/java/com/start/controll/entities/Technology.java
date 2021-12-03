package com.start.controll.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Technology {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotEmpty(message = "Nome da tecnologia não pode ser vazia")
  @Size(max = 255, message = "Nome muito long")
  private String name;

  @Column(nullable = false)
  @Lob
  @NotEmpty(message = "Descrição da tecnologia não pode ser vazia")
  private String description;

  @OneToMany(mappedBy = "technology")
  private List<GroupDaily> groupList;

  public Technology(String name, String description) {
    this.name = name;
    this.description = description;
  }
}

