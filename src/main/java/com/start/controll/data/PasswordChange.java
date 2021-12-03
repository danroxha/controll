package com.start.controll.data;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordChange {

  @NotEmpty(message = "Senha atual não pode ser vazia")
  private String currentPassword;

  @NotEmpty(message = "Senha não pode ser vazia")
  private String password;

  @NotEmpty(message = "Confirmação de senha não pode ser vazia")
  private String passwordConfirm;

  public Boolean isValidPassword() {
    return password != null && passwordConfirm != null && password.equals(passwordConfirm);
  }

  public Boolean isValidCurrentPassword(String currentPassword) {
    return new BCryptPasswordEncoder().matches(this.currentPassword, currentPassword);
  }
}
