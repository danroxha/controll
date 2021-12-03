package com.start.controll.controllers;

import com.start.controll.entities.Project;
import com.start.controll.entities.User;
import com.start.controll.services.ModuleService;
import com.start.controll.services.ProjectService;
import com.start.controll.services.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("dashboard/projetos")
public class ProjectController {

  @Autowired
  private StarterService starterService;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ModuleService moduleService;

  @RequestMapping("adicionar")
  public ModelAndView addProject(@AuthenticationPrincipal User user, @RequestParam Long id) {

    var starter = starterService.findStarterById(id);

    if(starter.isEmpty())
      return new ModelAndView("redirect:/dashboard/starter/listar");

    return new ModelAndView("dashboard/project/form.html") {{
      addObject("user", user);
      addObject("modules", moduleService.findAllModules());
      addObject("starter", starter.get());
      addObject("project", new Project());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public  ModelAndView addProject(
      @AuthenticationPrincipal User user,
      @Valid Project project,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  )
  {

    if(bindingResult.hasErrors()) {

      return new ModelAndView("dashboard/project/form.html") {{
        addObject("user", user);
        addObject("starter", project.getStarter());
        addObject("modules", moduleService.findAllModules());
        addObject("message", "Corrija os erros abaixo");
        addObject("project", project);
      }};
    }
      var isUpdateAction = project.getId() != null;

      projectService.createProject(project);

      if(isUpdateAction)
        return new ModelAndView(String.format("redirect:/dashboard/projetos/overview?id=%s", project.getId()));

      return new ModelAndView(String.format("redirect:/dashboard/starter?id=%s", project.getStarter().getId()));
  }

  @RequestMapping("editar")
  public ModelAndView updateProject(@AuthenticationPrincipal User user, @RequestParam Long id) {
    var projectSaved = projectService.findProjetcById(id);

    if(projectSaved.isEmpty())
      return new ModelAndView("redirect:/dashboard");

    var project = projectSaved.get();

    return new ModelAndView("dashboard/project/form.html") {{
      addObject("user", user);
      addObject("starter", project.getStarter());
      addObject("modules", moduleService.findAllModules());
      addObject("project", project);
    }};
  }

  @RequestMapping("excluir")
  public ModelAndView deleteProject(@AuthenticationPrincipal User user, @RequestParam Long id) {
    var project = projectService.deleteProjectById(id);

    if(project.isEmpty())
      return new ModelAndView("redirect:/dashboard");

    return new ModelAndView(String.format("redirect:/dashboard/starter?id=%s", project.get().getStarter().getId()));
  }

  @RequestMapping("overview")
  public ModelAndView overviewProject(@AuthenticationPrincipal User user, @RequestParam Long id) {
    var project = projectService.findProjetcById(id);

    if(project.isEmpty())
      return new ModelAndView("redirect:/dashboard");

    return new ModelAndView("dashboard/project/details.html") {{
      addObject("user", user);
      addObject("project", project.get());
    }};
  }
}
