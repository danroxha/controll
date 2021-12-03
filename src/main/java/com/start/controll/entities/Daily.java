package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Daily {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "Data da daily deve ser uma data válida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  @Lob
  private String making;

  @Lob
  private String done;

  @Lob
  private String impediment;

  @NotNull
  private Boolean presence;

  @ManyToOne
  @JsonIgnoreProperties({"projects", "dailies"})
  private Starter starter;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties("scrumMaster")
  private GroupDaily groupDaily;

  @NotNull(message = "Módulo não pode ser vázio")
  @ManyToOne
  private Module module;

  public Daily(Date date, String making, String done, String impediment, Boolean presence, Starter starter, GroupDaily groupDaily, Module module) {
    this.date = date;
    this.making = making;
    this.done = done;
    this.impediment = impediment;
    this.presence = presence;
    this.starter = starter;
    this.groupDaily = groupDaily;
    this.module = module;
  }
}
