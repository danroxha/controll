package com.start.controll.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

  @OneToOne
  private Stage stage;

//  @OneToMany
//  private List<Daily> dailies;

  public Module(String name, Stage stage) {
    this.name = name;
    this.stage = stage;
  }
}
