package com.start.controll.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.start.controll.configuration.Constant.EMPLOYEE_PHOTO_FOLDER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Starter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Nome do Starter não pode ser vazio")
  @Length(max = 255, message = "Limite de 255 carateres  excedido no nome")
  private String name;

  @Column(nullable = false, unique = true)
  @Length(max = 4, min = 4, message = "Usuário deve ter examente 4 letras")
  @NotEmpty(message = "Usuário 4 letras do usuário não pode ser vazio")
  private String code;

  @Size(max = 255, message = "Nome da foto muito longo")
  private String photo;

  @ManyToOne
  private StartersProgram  program;

  @ManyToOne
  private Group group;

  @Transient
  public String getPhotoURL() {
    if (photo == null || id == null) return null;

    return String.format("%s/%s/%s", EMPLOYEE_PHOTO_FOLDER, id, photo);
  }


  public Starter(String name, String code, StartersProgram program) {
    this.name = name;
    this.code = code;
    this.program = program;
  }

  public Starter(String name, String code, StartersProgram program, Group group) {
    this(name, code, program);
    this.group = group;
  }
}
