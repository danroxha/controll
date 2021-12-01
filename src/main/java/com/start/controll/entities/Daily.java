package com.start.controll.entities;

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

  @OneToMany
  private List<Starter> starter;

  @ManyToOne(cascade = CascadeType.ALL)
  private GroupDaily groupDaily;
//
//  @OneToOne
//  private Module module;
}
