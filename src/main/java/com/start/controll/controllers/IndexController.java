package com.start.controll.controllers;

import com.start.controll.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

  @RequestMapping
  public ModelAndView index(@AuthenticationPrincipal User user) {

    System.out.println(user);

    if(user == null)
      return new ModelAndView("redirect:/login");

    if(user.getRolesList().contains("SCRUM_MASTER"))
      return new ModelAndView("redirect:/dashboard/daily");

    return new ModelAndView("redirect:/dashboard/tools");
  }
}
