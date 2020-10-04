package com.gemerbarbier.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NonNull
    public String date;

    @NonNull
    public String time;

    @NonNull
    public String name;

    @NonNull
    public String surname;

    @NonNull
    public String email;

    @NonNull
    public String phoneNumber;

    @NonNull
    public String barber; 

    public String note;

    @NonNull
    public String cutType;

    @NonNull
    public String cutTag;

    public String color;

    public String startDateTime;

    public String endDateTime;
}