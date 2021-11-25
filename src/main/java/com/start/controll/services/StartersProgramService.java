package com.start.controll.services;

import com.start.controll.entities.StartersProgram;
import com.start.controll.repositories.StartersProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StartersProgramService {

  @Autowired
  private StartersProgramRepository startersProgramRepository;

  public Boolean isEmptyStartsProgramRepository() {
    return startersProgramRepository.count() == 0;
  }

  public List<StartersProgram> createManyProgram(List<StartersProgram> programs) {
    return startersProgramRepository.saveAll(programs);
  }

  public Optional<StartersProgram> findFirstProgram() {
    return startersProgramRepository.findAll().stream().findFirst();
  }
}
