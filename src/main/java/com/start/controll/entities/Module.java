package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Module {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private String name;

  @ManyToOne
  @JsonIgnoreProperties({"modules"})
  private Stage stage;

  @OneToMany(mappedBy = "module")
  @JsonIgnoreProperties({"module"})
  private List<GroupDaily> groupList;

  public Module(String name, Stage stage) {
    this.name = name;
    this.stage = stage;
  }
}
