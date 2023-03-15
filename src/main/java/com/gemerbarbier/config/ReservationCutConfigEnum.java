package com.gemerbarbier.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationCutConfigEnum {

  BEARD(20L, "orange", "Úprava brady"),
  BASIC_CUT(20L, "green", "Rýchly strih"),
  BASIC_BEARD(40L, "indigo", "Rýchly strih + úprava brady"),
  EXCLUSIVE_CUT(40L, "red", "Exclusive strih"),
  EXCLUSIVE_BEARD(60L, "purple", "Exclusive strih + úprava brady");

  private final Long cutTime;
  private final String cutColor;
  private final String cutName;
}
