package com.start.controll.controllers;

import java.util.List;

import javax.validation.Valid;

import com.start.controll.entities.Stage;
import com.start.controll.entities.User;
import com.start.controll.services.StageService;

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

@Controller
@RequestMapping("dashboard/tools/estagio")
public class StageController {
  
  @Autowired
  private StageService stageService;

  @RequestMapping
  public ModelAndView listStage(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/stage/list.html") {{
      addObject("user", user);
      addObject("stages", stageService.findAllStage());
    }};
  }
    

  @RequestMapping("adicionar")
  public ModelAndView addStage(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/stage/form.html") {
      {
        addObject("user", user);
        addObject("stage", new Stage());
      }
    };
  }


  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView addStage(
      @AuthenticationPrincipal User user,
      @Valid Stage stage,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      return new ModelAndView("dashboard/tools/stage/form.html") {
        {
          addObject("user", user);
          addObject("stage", stage);
        }
      };
    }

    var isUpdateAction = stage.getId() != null;

    stageService.saveStage(stage);

    if(isUpdateAction) {
      redirectAttributes.addFlashAttribute("message", String.format("Estágio '%s' foi atualizado", stage.getName()));
      return new ModelAndView("redirect:/dashboard/tools/estagio");
    }

    return new ModelAndView("redirect:/dashboard/tools/estagio/adicionar");
  }


  @RequestMapping("editar")
  public ModelAndView updateStage(@AuthenticationPrincipal User user, @RequestParam Long id) {
    
    var stage = stageService.findStageById(id);

    if(stage.isEmpty()) {
      return new ModelAndView("redirect:/dashboard/tools/estagio");
    }
    
    return new ModelAndView("dashboard/tools/stage/form.html") {{
      addObject("user", user);
      addObject("stage", stage.get());
    }};
  }

  @RequestMapping("excluir")
  public ModelAndView deleteStage(
    @RequestParam Long id, 
    RedirectAttributes redirectAttributes
  ) {
    
    var stageDeleted = stageService.deleteById(id);

    if(stageDeleted.isEmpty())
      redirectAttributes.addFlashAttribute("message", "Não pode remover estágio");
    else
      redirectAttributes.addFlashAttribute("message", String.format("Estágio '%s' foi removido", stageDeleted.get().getName()));


    return new ModelAndView("redirect:/dashboard/tools/estagio");
  }

  @RequestMapping("json")
  @ResponseBody
  public List<Stage> json() {
    return stageService.findAllStage();
  }

}
