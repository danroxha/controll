package com.start.controll.controllers;

import com.start.controll.entities.GroupDaily;
import com.start.controll.entities.RegisterDaily;
import com.start.controll.entities.User;
import com.start.controll.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("dashboard/daily/grupos")
public class GroupDailyController {

  @Autowired
  private GroupService groupService;

  @Autowired
  private TechnologyService technologyService;

  @Autowired
  private StartersProgramService startersProgramService;

  @Autowired
  private UserService userService;

  @Autowired
  private ModuleService moduleService;

  @RequestMapping
  public ModelAndView listDailyGroups(
      @AuthenticationPrincipal User user,
      @RequestParam(required = false) Optional<Long> id
  ) {

    if(id.isPresent()) {
      var group = groupService.findDailyGroupById(id.get());

      if(group.isEmpty())
        return new ModelAndView("redirect:/dashboard/daily/grupos");


      return new ModelAndView("dashboard/daily/dailies.html") {{
        addObject("user", user);
        addObject("group", group.get());
      }};
    }

    List<GroupDaily> groups;

    if(user.getRolesList().contains("SCRUM_MASTER"))
      groups = groupService.findByScrumMaster(user);
    else
      groups = groupService.findAllGroups();

    return new ModelAndView("dashboard/daily/list.html") {{
      addObject("user", user);
      addObject("groups", groups);
    }};
  }

  @RequestMapping("adicionar")
  public ModelAndView addGroup(@AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {

    String message = "Adicione %s para prosseguir como criação do grupo de Dailies";

    var technologies = technologyService.findAllTechnologies();
    if(technologies.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format(message, "uma tecnologia"));
      return new ModelAndView("redirect:/dashboard/tools/tecnologia/adicionar");
    }

    var scmList = userService.findAllScrumMaster();
    if(scmList.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format(message, "um SCRUM MASTER"));
      return new ModelAndView("redirect:/dashboard/tools/usuarios/adicionar");
    }

    var startersProgram = startersProgramService.findAllProgram();
    if(startersProgram.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format(message, "uma turma de Starters"));
      return new ModelAndView("redirect:/dashboard/turma/adicionar");
    }

    var modules = moduleService.findAllModules();
    if(modules.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format(message, "um módulo"));
      return new ModelAndView("redirect:/dashboard/tools/modulos/adicionar");
    }

    return new ModelAndView("dashboard/daily/form.html") {{
      addObject("modules", modules);
      addObject("user", user);
      addObject("group", new GroupDaily());
      addObject("startersProgram", startersProgram);
      addObject("technologies", technologies);
      addObject("scmList", scmList);
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView addGroup(
      @AuthenticationPrincipal User user,
      @Valid GroupDaily group,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    if(bindingResult.hasErrors()) {
      System.err.println();

      return new ModelAndView("dashboard/daily/form.html") {{
        addObject("user", user);
        addObject("modules", moduleService.findAllModules());
        addObject("group", group);
        addObject("startersProgram", startersProgramService.findAllProgram());
        addObject("message", "Corrija os erros abaixo");
        addObject("technologies", technologyService.findAllTechnologies());
        addObject("scmList", userService.findAllScrumMaster());
        addObject("errorEmptyStarersList", bindingResult.getFieldErrors("starters"));
      }};
    }

    var isUpdateAction = group.getId() != null;

    groupService.createGroup(group);

    if(isUpdateAction)
      return new ModelAndView(String.format("redirect:/dashboard/daily/grupos?id=%s", group.getId()));

    redirectAttributes.addFlashAttribute("message", String.format("Grupo '%s' foi registrado com sucesso", group.getName()));

    return new ModelAndView("redirect:/dashboard/daily/grupos/adicionar");
  }

  @RequestMapping("editar")
  public ModelAndView addGroup(@AuthenticationPrincipal User user, @RequestParam Long id) {

    var group = groupService.findDailyGroupById(id);


    if(group.isEmpty())
      return new ModelAndView("redirect:/dashboard/daily/grupos");


    return new ModelAndView("dashboard/daily/form.html") {{
      addObject("user", user);
      addObject("modules", moduleService.findAllModules());
      addObject("group", group.get());
      addObject("startersProgram", startersProgramService.findAllProgram());
      addObject("technologies", technologyService.findAllTechnologies());
      addObject("scmList", userService.findAllScrumMaster());
    }};
  }

  @RequestMapping("excluir")
  public ModelAndView addGroup(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    var groupDeleted = groupService.deleteDailyGroupById(id);

    if(groupDeleted.isEmpty())
      redirectAttributes.addFlashAttribute("message", String.format("Grupo com ID '%s' não foi encontrado"));
    else
      redirectAttributes.addFlashAttribute("message", String.format("Grupo '%s' foi removido", groupDeleted.get().getName()));

    return new ModelAndView("redirect:/dashboard/daily/grupos");
  }

  @RequestMapping("registrar")
  public ModelAndView dailyGroup(@AuthenticationPrincipal User user, @RequestParam Long id) {
    var group = groupService.findDailyGroupById(id);

    if(group.isEmpty())
      return new ModelAndView("redirect:/dashboard/daily/grupos");


    return new ModelAndView("dashboard/daily/register.html") {{
      addObject("user", user);
      addObject("group", group.get());
      addObject("register", new RegisterDaily());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "registrar")
    public ModelAndView dailyGroup(@AuthenticationPrincipal User user, RegisterDaily register, BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      return new ModelAndView("dashboard/daily/daily_list.html") {{
        addObject("user", user);
        addObject("group", register.getDailyGroup());
        addObject("message", "Corrija os erros abaixo");
        addObject("register", register);
      }};
    }

    groupService.registerDaily(register);

    return new ModelAndView(String.format("redirect:/dashboard/daily/grupos?id=%s", register.getDailyGroup().getId()));
  }

  @RequestMapping("registro/editar")
  public ModelAndView updateDailyGroup(@AuthenticationPrincipal User user, Long id) {

    var register = groupService.findRegisterById(id);

    if(register.isEmpty())
      return new ModelAndView("redirect:/dashboard/daily/grupos");

    return new ModelAndView("dashboard/daily/register.html") {{
      addObject("user", user);
      addObject("group", register.get().getDailyGroup());
      addObject("register", register.get());
    }};
  }

  @RequestMapping("registro/excluir")
  public ModelAndView deleteDailyRegister( Long id, RedirectAttributes redirectAttributes) {

    var register = groupService.deleteRegisterById(id);

    if(register.isEmpty()) {
      return new ModelAndView("redirect:/dashboard/daily/grupos");
    }

    redirectAttributes.addFlashAttribute(String.format("message", "Daily do dia '%s' foi removida", register.get().getDate()));

    return new ModelAndView(String.format("redirect:/dashboard/daily/grupos?id=%s", register.get().getDailyGroup().getId()));
  }
  @RequestMapping("json")
  @ResponseBody
  public List<GroupDaily> listJSON() {
    return groupService.findAllGroups();
  }

}
