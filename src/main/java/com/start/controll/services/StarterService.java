package com.start.controll.services;

import com.start.controll.entities.Starter;
import com.start.controll.repositories.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarterService {

  @Autowired
  private StarterRepository starterRepository;

  public List<Starter> createManyStarters(List<Starter> starters) {
    return starterRepository.saveAll(starters);
  }

  public Boolean isEmptyStartRepository() {
    return starterRepository.count() == 0;
  }

  public List<Starter> findAllStarters() {
    return starterRepository.findAll();
  }
}
