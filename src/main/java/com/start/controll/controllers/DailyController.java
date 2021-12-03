package com.start.controll.controllers;

import com.start.controll.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("dashboard/daily")
public class DailyController {

  @RequestMapping
  public ModelAndView index(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/daily/index"){{
      addObject("user", user);
    }};
  }
}
