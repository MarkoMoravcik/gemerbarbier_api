package com.gemerbarbier.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "time", "barber"})})
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String date;

  @NonNull
  private String time;

  @NonNull
  private String name;

  @NonNull
  private String surname;

  private String email;

  private String phoneNumber;

  @NonNull
  private String barber;

  private String note;

  private String cutType;

  @NonNull
  private String cutTag;

  private String color;

  private String startDateTime;

  private String endDateTime;
}
