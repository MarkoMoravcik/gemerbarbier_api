package com.gemerbarbier.exception;

public class CreateReservationException extends RuntimeException {

  private static final long serialVersionUID = 2194523997557273025L;

  public CreateReservationException(final String message) {
    super(message);
  }
}
