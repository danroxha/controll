package com.start.controll.services;

import com.start.controll.entities.StartersProgram;
import com.start.controll.repositories.StartersProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

  public List<StartersProgram> findAllProgram() {
    return startersProgramRepository.findAll(
        Sort.by("begin").descending()
    );
  }

  public Optional<StartersProgram> updateProgram(StartersProgram program) {
    return Optional.ofNullable(startersProgramRepository.save(program));
  }

  public Optional<StartersProgram> createProgram(StartersProgram startersProgram) {
    return updateProgram(startersProgram);
  }

  public Optional<StartersProgram> findProgramById(Long id) {
    return startersProgramRepository.findById(id);
  }

  public Optional<StartersProgram> deleteProgramById(Long id) {
    var startersProgramSaved = findProgramById(id);

    if(startersProgramSaved.isEmpty())
      return Optional.empty();

//    if(!startersProgramSaved.get().getStarters().isEmpty())
//      return Optional.empty();

    startersProgramRepository.deleteById(id);

    return startersProgramSaved;
  }
}
