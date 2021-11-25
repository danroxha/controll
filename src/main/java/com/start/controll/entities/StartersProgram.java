package com.start.controll.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartersProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Nome da turma não pode ser vazia")
  @Size(max = 255, message = "Nome muito longo")
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @NotNull(message = "Data de inicio de programa deve ser uma data valida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date begin;

  @Column(nullable = false)
  @NotNull(message = "Data de fim do programa deve ser uma data valida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date end;

  public StartersProgram(String name, Date begin, Date end) {
    this.name = name;
    this.begin = begin;
    this.end = end;
  }
}
