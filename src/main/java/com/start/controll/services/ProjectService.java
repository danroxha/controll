package com.start.controll.services;

import com.start.controll.entities.Project;
import com.start.controll.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  public Optional<Project> createProject(Project project) {
    return Optional.ofNullable(projectRepository.save(project));
  }

  public Optional<Project> findProjetcById(Long id) {
    return projectRepository.findById(id);
  }

  public Optional<Project> deleteProjectById(Long id) {
    var project = findProjetcById(id);

    if(project.isEmpty())
      return Optional.empty();

    projectRepository.deleteById(id);

    return project;
  }
}
