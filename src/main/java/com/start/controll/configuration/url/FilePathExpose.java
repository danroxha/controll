package com.start.controll.configuration.url;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.start.controll.configuration.Constant.EMPLOYEE_PHOTO_FOLDER;

@Configuration
public class FilePathExpose implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    File diretory = new File(EMPLOYEE_PHOTO_FOLDER);
    if(!diretory.exists()) {
      diretory.mkdirs();
    }

    exposeDirectory(EMPLOYEE_PHOTO_FOLDER, registry);
  }

  private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
    Path uploadDir = Paths.get(dirName);
    String uploadPath = uploadDir.toFile().getAbsolutePath();

    if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

    registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
  }
}
