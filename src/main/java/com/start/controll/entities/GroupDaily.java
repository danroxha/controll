package com.start.controll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDaily {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 255, message = "Nome muito longo")
  private String name;

  @ManyToOne
  @JsonIgnoreProperties({"technology", "groupList"})
  private Technology technology;

  @ManyToOne
  @JsonIgnoreProperties({"username", "authorities", "starters", "dailies", "password",
      "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "roles", "active"
  })
  private User scrumMaster;

  @ManyToMany
  @NotNull(message = "Selecione algum integrante")
  @JsonIgnoreProperties({"dailies", "startersProgram", "starters", "projects", "photo"})
  private List<Starter> starters;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "dailyGroup")
  @JsonIgnoreProperties({"dailyGroup", "groupDaily", "module"})
  @OrderBy("date DESC")
  private List<RegisterDaily> dailies;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  @ManyToOne
  @JsonIgnoreProperties({"module", "groupList"})
  private Module module;

  @PrePersist
  @PreUpdate
  private void onCreateOrUpdate() {
    lastModifiedDate = new Date();
  }

  public GroupDaily(String name, Technology technology, User scrumMaster, List<Starter> starters, Module module) {
    this.name = name;
    this.technology = technology;
    this.scrumMaster = scrumMaster;
    this.starters = starters;
    this.module = module;
  }
}

