package com.start.controll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var userSaved = userService.findUserByEmail(email);

    if(userSaved.isEmpty())
      return null;

    return userSaved.get();
  }
}
