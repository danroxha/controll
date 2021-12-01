package com.start.controll.entities;

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
  private Technology technology;

  @ManyToOne
  private User scrumMaster;

  @ManyToMany
  @NotNull(message = "Selecione algum integrante")
  private List<Starter> starters;

  @OneToMany(cascade = CascadeType.ALL)
  private List<RegisterDaily> dailies;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  @PrePersist
  @PreUpdate
  private void onCreateOrUpdate() {
    lastModifiedDate = new Date();
  }

}

