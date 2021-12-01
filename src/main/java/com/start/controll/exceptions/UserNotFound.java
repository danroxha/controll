package com.start.controll.exceptions;

public class UserNotFound extends Exception {
  public UserNotFound() {
    this("User not found");
  }

  public UserNotFound(String message) {
    super(message);
  }
}
