package com.start.controll.services;

import com.start.controll.entities.User;
import com.start.controll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Optional<User> createUser(User user) throws Exception {

    if(findUserByEmail(user.getEmail()).isPresent()) {
      return Optional.empty();
    }

    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return Optional.ofNullable(userRepository.save(user));
  }

  public List<User> createManyUsers(User ...users) {
    var userList = Arrays
        .stream(users)
        .map(user -> {
          user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
          return user;
        })
        .filter(user -> findUserByEmail(user.getEmail()).isEmpty())
        .collect(Collectors.toList());

    return userRepository.saveAll(userList);
  }

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  public Boolean isRepositoryEmpty() {
    return userRepository.count() == 0;
  }
}
