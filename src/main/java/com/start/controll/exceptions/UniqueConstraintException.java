package com.start.controll.exceptions;



public class UniqueConstraintException extends Exception {
  public UniqueConstraintException() {
    super("Valor duplicado");
  }

  public UniqueConstraintException(String message) {
    super(message);
  }
}
