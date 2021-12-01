package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.start.controll.configuration.Constant.EMPLOYEE_PHOTO_FOLDER;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Starter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Nome do Starter não pode ser vazio")
  @Size(max = 255, message = "Limite de 255 carateres  excedido no nome")
  private String name;

  @Column(nullable = false, unique = true)
  @Size(max = 4, min = 4, message = "Usuário deve ter examente 4 letras")
  @NotEmpty(message = "Usuário 4 letras do usuário não pode ser vazio")
  private String code;

  @Size(max = 255, message = "Nome da foto muito longo")
  private String photo;

  @NotNull(message = "Turma do Starter não pode ser vazia")
  @ManyToOne
  @JsonIgnoreProperties("starters")
  private StartersProgram  startersProgram;

  @OneToMany(mappedBy = "starter", cascade = CascadeType.ALL)
  private List<Project> projects;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Daily> dailies;


  @Transient
  public String getPhotoURL() {
    if (photo == null || id == null) return null;

    return String.format("%s/%s/%s", EMPLOYEE_PHOTO_FOLDER, id, photo);
  }

  public Starter(String name, String code, StartersProgram startersProgram) {
    this.name = name;
    this.code = code;
    this.startersProgram = startersProgram;
  }

  public Starter(String name, String code, StartersProgram program, GroupDaily daily) {
    this(name, code, program);
//    this.group = group;
  }

}
