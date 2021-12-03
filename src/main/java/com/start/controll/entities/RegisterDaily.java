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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDaily {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "Data da daily deve ser uma data válida")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  @ManyToOne
  @JsonIgnoreProperties({"dailyGroup", "groupDaily", "starters"})
  private GroupDaily dailyGroup;

  @OneToMany(cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"starters", "groupDaily", "module"})
  private List<Daily> dailies;

  public RegisterDaily(Date date, GroupDaily dailyGroup, List<Daily> dailies) {
    this.date = date;
    this.dailyGroup = dailyGroup;
    this.dailies = dailies;
  }
}
