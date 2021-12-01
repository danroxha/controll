package com.start.controll.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(max = 255, message = "Nome muito longo")
  private String name;

  @Column(unique = true)
  @Email
  private String email;

  @Column(nullable = false)
  @Size(max = 255, message = "Senha muito longa")
  private String password;

  private Boolean active = true;

  private String roles;

  public User(String name, String email, String password, String roles) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public List<String> getRolesList() {
    if(roles.length() > 0) {
      return Arrays.asList(this.roles.split(","));
    }

    return new ArrayList<>();
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    var authorities = new ArrayList<GrantedAuthority>();

    getRolesList().forEach(role -> {
      GrantedAuthority  authority = new SimpleGrantedAuthority("ROLE_" + role);
      authorities.add(authority);
    });

    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
