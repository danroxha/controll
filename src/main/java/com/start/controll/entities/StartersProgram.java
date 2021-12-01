package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StartersProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Nome da turma não pode ser vazia")
  @Size(max = 255, message = "Nome muito longo")
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @NotNull(message = "Data de inicio da turma deve ser uma data válida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date begin;

  @Column(nullable = false)
  @NotNull(message = "Data de fim da turma deve ser uma data valida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date end;

  @OrderBy("name ASC")
  @JsonIgnoreProperties("startersProgram")
  @OneToMany(mappedBy = "startersProgram")
  private List<Starter> starters;

  public StartersProgram(String name, Date begin, Date end) {
    this.name = name;
    this.begin = begin;
    this.end = end;
  }

}
