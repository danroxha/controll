package com.start.controll.controllers;

import com.start.controll.entities.User;
import com.start.controll.exceptions.UserNotFound;
import com.start.controll.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("dashboard/scm")
public class ScrumMasterController {

  @Autowired
  private UserService userService;

  @RequestMapping
  public ModelAndView index(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/scm/list.html") {{
      addObject("user", user);
      addObject("scrumMasterList", userService.findAllScrumMaster());
    }};
  }

  @RequestMapping("status")
  public ModelAndView enableOrDisableSCRUMMaster(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
      var user = userService.enableOrDisableUser(id);
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Usuário '%s' foi %s", user.getName(), user.isEnabled() ? "Ativado" : "Desativado")
      );
    }
    catch (UserNotFound u) {
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Usuário com ID '%s' não foi encontrado", id)
      );
    }

    return new ModelAndView("redirect:/dashboard/scm");
  }

  @RequestMapping("json")
  @ResponseBody
  public List<User> listUsers() {
    return userService.findAllScrumMaster();
  }

}
