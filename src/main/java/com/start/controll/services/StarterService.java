package com.start.controll.services;

import com.start.controll.entities.Starter;
import com.start.controll.exceptions.UniqueConstraintException;
import com.start.controll.repositories.StarterRepository;
import com.start.controll.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.start.controll.configuration.Constant.EMPLOYEE_PHOTO_FOLDER;

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
    return starterRepository.findAll(Sort.by("name").ascending());
  }

  public Optional<Starter> saveStarter(Starter starter) throws Exception {

    if(findByCode(starter.getCode()).isPresent() && starter.getId() == null)
      throw new UniqueConstraintException("Codigo já existe no sistema");

    starter.setCode(starter.getCode().toUpperCase(Locale.ROOT));

    return Optional.ofNullable(starterRepository.save(starter));
  }

  public Optional<Starter> saveStarter(Starter starter, MultipartFile multipartFile) throws Exception {

      var extensionFile = FileUploadUtil.getFileExtension(multipartFile.getOriginalFilename());
      String coverName = StringUtils.cleanPath(String.format("%s.%s", System.nanoTime(), extensionFile));
      starter.setPhoto(coverName);

      if(starter.getId() != null) {
        FileUploadUtil.deleteDirectory(String.format("%s/%s", EMPLOYEE_PHOTO_FOLDER, starter.getId()));
      }

      var starterSaved = saveStarter(starter);

      var uploadDir = String.format("%s/%s", EMPLOYEE_PHOTO_FOLDER, starterSaved.get().getId());

      FileUploadUtil.saveFile(uploadDir, coverName, multipartFile);

      return starterSaved;
  }

  public List<Starter> findAllByPage(Integer page) {
    var size = 5;
    return findAllByPage(page, size);
  }

  public List<Starter> findAllByPage(Integer page, Integer size){
    Pageable firstPageWithTwoElements = PageRequest.of(page, size);
    return starterRepository.findAll(firstPageWithTwoElements).getContent();
  }

  public Optional<Starter> findStarterById(Long id) {
    return starterRepository.findById(id);
  }

  public Optional<Starter> removeStarterById(Long id) throws IOException {

    var starter = findStarterById(id);

    if(starter.isEmpty()) {
      return Optional.empty();
    }

    starterRepository.deleteById(id);
    FileUploadUtil.deleteDirectory(String.format("%s/%s", EMPLOYEE_PHOTO_FOLDER, starter.get().getId()));

    return starter;
  }

  public Optional<Starter> findByCode(String code) {
    return starterRepository.findByCode(code);
  }
}
