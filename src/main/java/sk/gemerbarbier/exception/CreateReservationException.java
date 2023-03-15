package com.gemerbarbier.exception;

import java.io.Serial;

public class CreateReservationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2194523997557273025L;

  public CreateReservationException(final String message) {
    super(message);
  }
}
