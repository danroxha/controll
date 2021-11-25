package com.start.controll.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Technology tecnology;

  @ManyToOne
  private User scrumMaster;

  @ManyToMany
  private List<Starter> starters;

  public Group(Technology tecnology, User scrumMaster, List<Starter> starters) {
    this.tecnology = tecnology;
    this.scrumMaster = scrumMaster;
    this.starters = starters;
  }
}

