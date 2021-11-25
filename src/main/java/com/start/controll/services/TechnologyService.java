package com.start.controll.services;

import com.start.controll.entities.Technology;
import com.start.controll.repositories.TecnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyService {

  @Autowired
  private TecnologyRepository tecnologyRepository;

  public Optional<Technology> createTecnology(Technology tecnology) {
    return Optional.ofNullable(tecnologyRepository.save(tecnology));
  }

  public Optional<Technology> saveTechnology(Technology tecnology) {
    return createTecnology(tecnology);
  }

  public Boolean isEmptyTechnologyRepository() {
    return tecnologyRepository.count() == 0;
  }

  public List<Technology> createManyTecnologies(List<Technology> technologies) {
    return tecnologyRepository.saveAll(technologies);
  }
}
