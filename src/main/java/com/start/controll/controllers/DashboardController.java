package com.start.controll.controllers;

import com.start.controll.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController
@RequestMapping("dashboard")
public class DashboardController {

  @RequestMapping
  public ModelAndView index(@AuthenticationPrincipal User user) {
    System.err.println(user.getRolesList());
    return new ModelAndView("dashboard/index") {{
      addObject("user", user);
    }};
  }
}
