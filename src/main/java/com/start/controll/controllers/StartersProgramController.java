package com.start.controll.controllers;

import com.start.controll.entities.StartersProgram;
import com.start.controll.entities.User;
import com.start.controll.services.StartersProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("dashboard")
public class StartersProgramController {

  @Autowired
  private StartersProgramService startersProgramService;

  @RequestMapping("turma")
  public ModelAndView index(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/startersProgram/index.html") {{
      addObject("user", user);
    }};
  }

  @RequestMapping("turma/adicionar")
  public ModelAndView addProgram(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/startersProgram/form.html"){{
      addObject("user", user);
      addObject("startersProgram", new StartersProgram());
    }};
  }

  @RequestMapping("turma/editar")
  public ModelAndView updateProgram(@AuthenticationPrincipal User user, @RequestParam Long id) {

    var startersProgram = startersProgramService.findProgramById(id);

    if(startersProgram.isEmpty())
      return new ModelAndView("redirect:/dashboard/turma/listar");

    return new ModelAndView("dashboard/startersProgram/form.html"){{
      addObject("user", user);
      addObject("startersProgram", startersProgram.get());
    }};
  }

  @RequestMapping("turma/excluir")
  public ModelAndView deleteProgram(@AuthenticationPrincipal User user, @RequestParam Long id, RedirectAttributes redirectAttributes) {
    var startersProgramDeleted = startersProgramService.deleteProgramById(id);

    if(startersProgramDeleted.isPresent())
      redirectAttributes.addFlashAttribute("message", String.format("Turma %s foi removida", startersProgramDeleted.get().getName()));
    else
      redirectAttributes.addFlashAttribute("message", String.format("Turma com ID %s não pode ser removida", id));

    return new ModelAndView("redirect:/dashboard/turma/listar");
  }

  @RequestMapping(method = RequestMethod.POST, path = "turma/adicionar")
  public ModelAndView addProgram(
      @Valid StartersProgram startersProgram,
      BindingResult bindingResult,
      @AuthenticationPrincipal User user,
      RedirectAttributes redirectAttributes
  ) {

    redirectAttributes.addFlashAttribute("hasErrors", false);

    if(bindingResult.hasErrors()) {

      redirectAttributes.addFlashAttribute("hasErrors", true);

      return new ModelAndView("dashboard/startersProgram/form.html"){{
        addObject("user", user);
        addObject("startersProgram", startersProgram);
        addObject("message", "Corrija os erros abaixo");
      }};
    }

    var isUpdateAction = startersProgram.getId() != null;

    startersProgramService.createProgram(startersProgram);

    if(isUpdateAction) {
      return new ModelAndView("redirect:/dashboard/turma/listar");
    }

    return new ModelAndView("dashboard/startersProgram/form.html"){{
      addObject("user", user);
      addObject("startersProgram", new StartersProgram());
      addObject("message", "Turma salva com sucesso");
    }};
  }

  @RequestMapping("turma/listar")
  public ModelAndView listProgram(@AuthenticationPrincipal User user)   {

    var programs = startersProgramService.findAllProgram();

    return new ModelAndView("dashboard/startersProgram/list.html"){{
      addObject("user", user);
      addObject("programs", programs);
    }};
  }

  @RequestMapping("turma/listar/json")
  @ResponseBody
  public List<StartersProgram> listProgramJSON(@AuthenticationPrincipal User user) {
    return startersProgramService.findAllProgram();
  }
  @RequestMapping("turma/json")
  @ResponseBody
  public Optional<StartersProgram> listProgramJSON(
      @AuthenticationPrincipal User user,
      @RequestParam Long id
  ) {
    return startersProgramService.findProgramById(id);
  }
}
