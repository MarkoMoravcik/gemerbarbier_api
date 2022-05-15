package com.gemerbarbier.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationCutConfigEnum {
    
    BEARD(20l, "orange", "Úprava brady"),
    BASIC_CUT(20l,"green", "Rýchly strih"),
    BASIC_BEARD(40l, "indigo", "Rýchly strih + úprava brady"),
    EXCLUSIVE_CUT(40l, "red", "Exclusive strih"),
    EXCLUSIVE_BEARD(60l, "purple", "Exclusive strih + úprava brady");
    
    Long cutTime;
    String cutColor;
    String cutName;
}
