package com.start.controll.services;

import com.start.controll.entities.Stage;
import com.start.controll.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageService {

  @Autowired
  private StageRepository stageRepository;

  public Optional<Stage> createStage(Stage stage) {
    return Optional.ofNullable(stageRepository.save(stage));
  }

  public Optional<Stage> updateOrCreateStage(Stage stage) {
    return createStage(stage);
  }

  public Boolean isEmptyStageRepository() {
    return stageRepository.count() == 0;
  }

  public List<Stage> createManyStage(List<Stage> stages) {
    return stageRepository.saveAll(stages);
  }

  public List<Stage> findAllStages() {
    return stageRepository.findAll(Sort.by("name").ascending());
  }
}
