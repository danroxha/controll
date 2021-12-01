package com.start.controll.controllers;

import com.start.controll.entities.Starter;
import com.start.controll.entities.User;
import com.start.controll.exceptions.UniqueConstraintException;
import com.start.controll.services.StarterService;
import com.start.controll.services.StartersProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("dashboard/starter")
public class StarterController {

  @Autowired
  private StarterService starterService;

  @Autowired
  private StartersProgramService startersProgramService;


  @RequestMapping
  public ModelAndView starters(@AuthenticationPrincipal User user, @RequestParam(required = false) Optional<Long> id) {

    if(id.isEmpty()) {
      return new ModelAndView("dashboard/starter/index.html") {{
        addObject("user", user);
      }};
    }

    var starter = starterService.findStarterById(id.get());

    if(starter.isEmpty()) {
      return new ModelAndView("redirect:/dashboard/starter/listar");
    }

    return new ModelAndView("dashboard/starter/details.html") {{
      addObject("user", user);
      addObject("starter", starter.get());
    }};
  }


  @RequestMapping("listar")
  public ModelAndView listStarters(@AuthenticationPrincipal User user) {

    var starters = starterService.findAllStarters();

    return new ModelAndView("dashboard/starter/list.html") {{
      addObject("user", user);
      addObject("starters", starters);
    }};
  }


  @RequestMapping("adicionar")
  public ModelAndView addStater(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/starter/form.html") {{
      addObject("user", user);
      addObject("starter", new Starter());
      addObject("startersProgram", startersProgramService.findAllProgram());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path="adicionar")
  public ModelAndView addStater(
      @Valid Starter starter,
      BindingResult bindingResult,
      @AuthenticationPrincipal User user,
      @RequestParam("image") MultipartFile multipartFile,
      RedirectAttributes redirectAttributes) throws Exception {

    if(bindingResult.hasErrors()) {

      return new ModelAndView("dashboard/starter/form.html") {{
        addObject("user", user);
        addObject("starter", starter);
        addObject("message", "Corrija os erros abaixo");
        addObject("startersProgram", startersProgramService.findAllProgram());
      }};
    }

    var isUpdateMethod = starter.getId() != null;
    try {
      var hasFile = !multipartFile.getOriginalFilename().isBlank();
      if (hasFile)
        starterService.saveStarter(starter, multipartFile);
      else
        starterService.saveStarter(starter);
    }
    catch(UniqueConstraintException e) {

      return new ModelAndView("dashboard/starter/form.html") {{
        addObject("user", user);
        addObject("starter", starter);
        addObject("message", "Corrija os erros abaixo");
        addObject("startersProgram", startersProgramService.findAllProgram());
        addObject("duplicateCodeError", e.getMessage());
      }};
    }

    if (isUpdateMethod) {
      return new ModelAndView(String.format("redirect:/dashboard/starter?id=%s", starter.getId()));
    }

    return new ModelAndView("dashboard/starter/form.html") {{
      addObject("user", user);
      addObject("starter", new Starter());
      addObject("message", "Starter salvo com sucesso");
    }};
  }

  @RequestMapping("excluir")
  public ModelAndView removeStarter(@RequestParam Long id, RedirectAttributes redirectAttributes) throws IOException {
    var starterRemoved = starterService.removeStarterById(id);

    if(starterRemoved.isEmpty()) {
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Não pode remover starter com ID %s", id));
    }
    else {
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Starter '%s' foi removido ", starterRemoved.get().getName()));
    }

    return new ModelAndView("redirect:/dashboard/starter/listar");
  }

  @RequestMapping("editar")
  public ModelAndView updateStarter(@AuthenticationPrincipal User user, @RequestParam Long id, RedirectAttributes redirectAttributes) {
    var starter = starterService.findStarterById(id);

    if(starter.isEmpty()) {
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Starter com ID '%s' não foi encontrado", id)
      );

      return new ModelAndView("redirect:/dashboard/starter/listar");
    }

    return new ModelAndView("dashboard/starter/form.html") {{
      addObject("user", user);
      addObject("starter", starter.get());
      addObject("startersProgram", startersProgramService.findAllProgram());
    }};
  }

  @RequestMapping("json")
  public List<Starter> listStarters(@RequestParam(required = false) Integer page) {

    if(page != null) {
      page = Math.max(1, page);
      return starterService.findAllByPage(page - 1);
    }


    return starterService.findAllStarters();
  }
}
