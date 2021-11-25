package com.start.controll.configuration.database;

import com.start.controll.entities.*;
import com.start.controll.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class PopulateDatabase {

  @Autowired
  private UserService userService;

  @Autowired
  private TechnologyService technologyService;

  @Autowired
  private StateService stateService;

  @Autowired
  private StartersProgramService startersProgramService;

  @Autowired
  private StarterService starterService;

  @Bean
  void populateTechnologyTable() {

    if(!technologyService.isEmptyTechnologyRepository())
      return;

    technologyService.createManyTecnologies(Arrays.asList(
      new Technology("java", "Java é uma linguagem de programação orientada a objetos desenvolvida na década de 90"),
      new Technology("dotnet", ".net', '.NET é um framework livre e de código aberto para os sistemas operacionais Windows, Linux e macOS.")
    ));
  }

  @Bean
  void populateStageTable() {

    if(!stateService.isEmptyStageRepository())
      return;

    stateService.createManyStage(List.of(
        new Stage("MVC", "Etapa de estudos sobre MVC"),
        new Stage("API", "Etapa de estudos sobre API"),
        new Stage("TDD", "Etapa de estudos sobre TDD")
    ));
  }

  @Bean
  void populateStartsProgramTable() {
    if(!startersProgramService.isEmptyStartsProgramRepository())
      return;

    final Integer start = 2021 - 1900;
    final Integer end = 2022 - 1900;

    startersProgramService.createManyProgram(List.of(
        new StartersProgram(
            "Turma A",
            new Date(start, Calendar.SEPTEMBER, 3),
            new Date(end, Calendar.FEBRUARY, 3)
        )
    ));
  }

  @Bean
  void populateStarterTable() {

    if(!starterService.isEmptyStartRepository())
      return;

    var program = startersProgramService.findFirstProgram().get();

    starterService.createManyStarters(List.of(
        new Starter("Dianne May", "DMAY", program),
        new Starter("Tim Miles", "TMLS", program),
        new Starter("Carmen Cole", "CCLE", program),
        new Starter("Juan West", "JWST", program),
        new Starter("William Mccoy", "WMCC", program),
        new Starter("Donald Chambers", "DCES", program),
        new Starter("Eddie Carr", "EDCR", program),
        new Starter("Cathy Duncan", "CDCA", program),
        new Starter("Melissa Castro", "MCTR", program),
        new Starter("Luis Carter", "LCTE", program),
        new Starter("Terra Mason", "TMSN", program)
    ));
  }

  @Bean
  public void populateUserTable() throws Exception {

    if(!userService.isRepositoryEmpty())
      return;

    var users = userService.createManyUsers(
        new User("Clecio Gomes", "clecio.silva@gft.com", "Gft2021", "ADMIN"),
        new User("Astrogildo Perrengue", "scm@example.com", "Gft2021", "SCRUM_MASTER")
    );
  }
}
