package com.start.controll.services;

import com.start.controll.entities.Module;
import com.start.controll.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

  @Autowired
  private ModuleRepository moduleRepository;

  public Optional<Module> createModule(Module module) {
    return Optional.ofNullable(moduleRepository.save(module));
  }

  public Optional<Module> updateModule(Module module) {
    return createModule(module);
  }

  public List<Module> findAllModules() {
    return moduleRepository.findAll();
  }

  public Optional<Module> createOrUpdateModule(Module module) {
    return createModule(module);
  }

  public boolean isModuleRepositoryEmpty() {
    return moduleRepository.count() == 0;
  }

  public List<Module> createManyModules(ArrayList<Module> modules) {
    return moduleRepository.saveAll(modules);
  }

  public Optional<Module> findModuleById(Long id) {
    return moduleRepository.findById(id);
  }

  public Optional<Module> deleteModuleById(Long id) {
    var module = findModuleById(id);

    if(module.isEmpty())
      return Optional.empty();

    if(module.get().getGroupList().size() != 0)
      return Optional.empty();

    moduleRepository.deleteById(id);

    return module;
  }
}
