package com.start.controll.configuration.database;

import com.start.controll.entities.*;
import com.start.controll.entities.Module;
import com.start.controll.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class PopulateDatabase {

  @Autowired
  private UserService userService;

  @Autowired
  private TechnologyService technologyService;

  @Autowired
  private StageService stateService;

  @Autowired
  private StartersProgramService startersProgramService;

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private StarterService starterService;

  @Autowired
  private GroupService groupService;

  @Bean
  void populateTechnologyTable() {

    if(!technologyService.isEmptyTechnologyRepository())
      return;

    technologyService.createManyTecnologies(Arrays.asList(
      new Technology("Java", "Java é uma linguagem de programação orientada a objetos desenvolvida na década de 90"),
      new Technology(".NET", ".NET é um framework livre e de código aberto para os sistemas operacionais Windows, Linux e macOS."),
      new Technology("Lua", "Lua é uma linguagem de script poderosa, eficiente, leve e incorporável. Ele suporta programação procedural, programação orientada a objetos, programação funcional, programação orientada a dados e descrição de dados.")
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

    final Integer start = 2020 - 1900;
    final Integer end = 2021 - 1900;

    startersProgramService.createManyProgram(List.of(
        new StartersProgram(
            "Turma A",
            new Date(start, Calendar.SEPTEMBER, 3),
            new Date(end, Calendar.FEBRUARY, 3)
        ),
        new StartersProgram(
            "Turma B",
            new Date(start + 1, Calendar.SEPTEMBER, 3),
            new Date(end + 2, Calendar.FEBRUARY, 3)
        )
    ));
  }

  @Bean
  void populateStarterTable() {

    if(!starterService.isEmptyStartRepository())
      return;

    var startersProgram = startersProgramService.findFirstProgram().get();

    starterService.createManyStarters(List.of(
        new Starter("Dianne May", "DMAY", startersProgram),
        new Starter("Tim Miles", "TMLS", startersProgram),
        new Starter("Carmen Cole", "CCLE", startersProgram),
        new Starter("Juan West", "JWST", startersProgram),
        new Starter("William Mccoy", "WMCC", startersProgram),
        new Starter("Donald Chambers", "DCES", startersProgram),
        new Starter("Eddie Carr", "EDCR", startersProgram),
        new Starter("Cathy Duncan", "CDCA", startersProgram),
        new Starter("Melissa Castro", "MCTR", startersProgram),
        new Starter("Luis Carter", "LCTE", startersProgram),
        new Starter("Terra Mason", "TMSN", startersProgram)
    ));
  }

  @Bean
  public void populateUserTable() throws Exception {

    if(!userService.isRepositoryEmpty())
      return;

    var users = userService.createManyUsers(
        new User(" Cordélios Oberon", "oberon-admin@sunny.com", "Lactea20NN", "ADMIN"),
        new User("Astrogildo Alcântra", "astrogildo-scm@sunny.com", "Lactea20NN", "SCRUM_MASTER"),
        new User("Martiano Luanova", "martiano-scm@sunny.com", "Lactea20NN", "SCRUM_MASTER")
    );
  }

  @Bean
  public void populateModuleTable() {

    if(!moduleService.isModuleRepositoryEmpty())
      return;

    var stages = stateService
        .findAllStages()
        .stream()
        .limit(3)
        .collect(Collectors.toList());

    var modulesName = List.of("MVC - Estudo", "API - Estudo", "Testes - Estudo")
        .stream()
        .sorted()
        .collect(Collectors.toList());

    var modules = new ArrayList<Module>();

    for(var i = 0; i < stages.size(); i++) {
      modules.add(
          new Module(modulesName.get(i), stages.get(i))
      );
    }

    moduleService.createManyModules(modules);
  }

  @Bean
  public void populateDailyGroup() {
    if(!groupService.isGroupRepositoryEmpty())
      return;

    var starters = starterService
        .findAllStarters()
        .stream()
        .limit(3)
        .collect(Collectors.toList());

    var java = technologyService.findTechnologyById(1L).get();
    var dotnet = technologyService.findTechnologyById(2L).get();

    var module = moduleService.findModuleById(1L).get();
    var scrumMaster = userService.findUserByEmail("astrogildo-scm@sunny.com").get();

    var group = groupService.createGroup(new GroupDaily(
        "Grupo 1 - Java - Desafio MVC", java, scrumMaster,  starters, module
    )).get();

    groupService.createGroup(new GroupDaily(
        "Grupo 2 - .NET - Desafio MVC", dotnet, scrumMaster,  starters, module
    )).get();

    final Integer start = 2021 - 1900;

    var registers1 = group.getStarters().stream().map(starter -> new Daily(
        new Date(start + 1, Calendar.DECEMBER, 2), String.format("Fazendo + %s", starter.getId()),
        String.format("Concluido + %s", starter.getId()),
        String.format("Impedimento + %s", starter.getId()),
        Boolean.TRUE,  starter, group,
        module)
    ).collect(Collectors.toList());

    var registers2 = group.getStarters().stream().map(starter -> new Daily(
        new Date(start + 1, Calendar.DECEMBER, 3), String.format("Fazendo + %s", starter.getId()),
        String.format("Concluido + %s", starter.getId()),
        String.format("Impedimento + %s", starter.getId()),
        Boolean.TRUE,  starter, group,
        module)
    ).collect(Collectors.toList());

    group.setDailies(
        List.of(
            new RegisterDaily(new Date(start + 1, Calendar.DECEMBER, 2), group, registers1),
            new RegisterDaily(new Date(start + 1, Calendar.DECEMBER, 3), group, registers2)
        )
    );

    groupService.updateOrCreateGroup(group);


  }
}
