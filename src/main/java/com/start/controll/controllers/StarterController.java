package com.start.controll.controllers;

import com.start.controll.entities.Starter;
import com.start.controll.services.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("dashboard/starter")
public class StarterController {

  @Autowired
  private StarterService starterService;

  @RequestMapping
  public List<Starter> listStarters() {
    return starterService.findAllStarters();
  }
}
