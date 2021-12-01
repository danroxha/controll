package com.start.controll.controllers;

import com.start.controll.entities.Module;
import com.start.controll.entities.Technology;
import com.start.controll.entities.User;
import com.start.controll.services.ModuleService;
import com.start.controll.services.StageService;
import com.start.controll.services.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RestController
@RequestMapping("dashboard/tools")
public class DashboardController {

  @Autowired
  private TechnologyService technologyService;

  @Autowired
  private StageService stageService;

  @Autowired
  private ModuleService moduleService;

  @RequestMapping
  public ModelAndView index(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/index") {{
      addObject("user", user);
    }};
  }

  @RequestMapping("tecnologia")
  public ModelAndView technologyIndex(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/technology/list.html") {{
      addObject("user", user);
      addObject("technologies", technologyService.findAllTechnologies());
    }};
  }

  @RequestMapping("tecnologia/adicionar")
  public ModelAndView saveTechnology(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/technology/form.html") {{
      addObject("user", user);
      addObject("technology", new Technology());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "tecnologia/adicionar")
  public ModelAndView saveTechnology(
      @AuthenticationPrincipal User user,
      @Valid Technology technology,
      BindingResult bindingResult
  ) {


    if(bindingResult.hasErrors()) {
      return new ModelAndView("dashboard/tools/technology/form.html") {{
        addObject("user", user);
        addObject("message", "Corrija os erros abaixo");
        addObject("technology", technology);
      }};
    }

    var isRedirectToPageTechnologies = technology.getId() != null;

    technologyService.saveTechnology(technology);

    if(isRedirectToPageTechnologies)
      return new ModelAndView("redirect:/dashboard/tools/tecnologia");

    return new ModelAndView("redirect:/dashboard/tools/tecnologia/adicionar");
  }

  @RequestMapping("tecnologia/editar")
  public ModelAndView saveTechnology(@AuthenticationPrincipal User user, Long id) {

    var technology =  technologyService.findTechnologyById(id);

    if(technology.isEmpty()) {
      return new ModelAndView("redirect:/dashboard/tools/tecnologia");
    }

    return new ModelAndView("dashboard/tools/technology/form.html") {{
      addObject("user", user);
      addObject("technology", technology.get());
    }};
  }


  @RequestMapping("tecnologia/excluir")
  public ModelAndView deleteTechnology(@AuthenticationPrincipal User user, Long id, RedirectAttributes redirectAttributes) {

    var technology =  technologyService.deleteTechnologyById(id);

    if(technology.isEmpty())
      redirectAttributes.addFlashAttribute("Tecnologia com ID '%s' não pode ser removido");
    else
      redirectAttributes.addFlashAttribute("Tecnologia '%s' foi removida", technology.get().getName());

    return new ModelAndView("redirect:/dashboard/tools/tecnologia");
  }

  @RequestMapping("modulos")
  public ModelAndView moduleIndex(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/module/list.html") {{
      addObject("user", user);
      addObject("modules", moduleService.findAllModules());
    }};
  }

  @RequestMapping("modulos/adicionar")
  public ModelAndView addModule(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/module/form.html") {{
      addObject("module", new Module());
      addObject("stages", stageService.findAllStages());
      addObject("user", user);
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "modulos/adicionar")
  public ModelAndView addModule(
      @AuthenticationPrincipal User user,
      @Valid Module module,
      BindingResult bindingResult
  ) {

    if(bindingResult.hasErrors()) {
      return new ModelAndView("dashboard/tools/module/form.html") {{
        addObject("module", module);
        addObject("message", "Corrija os erros abaixo");
        addObject("stages", stageService.findAllStages());
        addObject("user", user);
      }};
    }

    var isRedirectToModuleList = module.getId() != null;

    moduleService.createOrUpdateModule(module);

    if(isRedirectToModuleList)
      return new ModelAndView("redirect:/dashboard/tools/modulos/");

    return new ModelAndView("redirect:/dashboard/tools/modulos/adicionar");

  }

  @RequestMapping("modulos/editar")
  public ModelAndView updateModule(@AuthenticationPrincipal User user, @RequestParam Long id) {

    var module = moduleService.findModuleById(id);

    if(module.isEmpty())
      return new ModelAndView("redirect:/dashboard/toos/modulos/");

    return new ModelAndView("dashboard/tools/module/form.html") {{
      addObject("module", module.get());
      addObject("stages", stageService.findAllStages());
      addObject("user", user);
    }};
  }

}
