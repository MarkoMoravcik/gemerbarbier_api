package com.gemerbarbier.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.gemerbarbier.config.ReservationCutConfigEnum;
import com.gemerbarbier.config.ReservationTimeConfigConstants;
import com.gemerbarbier.data.Reservation;
import com.gemerbarbier.data.ReservationDates;
import com.gemerbarbier.data.ReservationTime;
import com.gemerbarbier.repository.ReservationDatesRepository;
import com.gemerbarbier.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository repository;

  @Autowired
  private ReservationDatesRepository datesRepository;

  public void createReservation(Reservation reservation) {

    LocalTime parsedTime = LocalTime.parse(reservation.getTime());
    String parsedDate = LocalDate.parse(reservation.getDate()).toString();
    ReservationCutConfigEnum cutType = ReservationCutConfigEnum.valueOf(reservation.getCutTag());

    reservation.setStartDateTime(createDateTime(parsedDate, parsedTime, 0L));
    reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, cutType.getCutTime()));
    reservation.setColor(cutType.getCutColor());
    reservation.setCutType(cutType.getCutName());

    repository.save(reservation);
  }

  public List<Reservation> getReservations(String barber) {
    return repository.findByBarber(barber);
  }

  private String createDateTime(String parsedDate, LocalTime parsedTime, Long plusMinutes) {
    return parsedDate + " " + parsedTime.plusMinutes(plusMinutes).toString();
  }

  public void deleteReservation(@NotNull Long id, @NotNull String barber) {
    Optional<Reservation> optReservation = repository.findByIdAndBarber(id, barber);
    if (optReservation.isPresent()) {
      Optional<ReservationDates> optDate = datesRepository.findByDateAndBarber(optReservation.get().getDate(), barber);
      if (optDate.isPresent()) {
        Optional<ReservationTime> optTime = optDate.get().getAvailableTimes().stream()
            .filter(t -> t.getTime().equals(optReservation.get().getTime())).findFirst();
        if (optTime.isPresent()) {
          updateTimes(optReservation.get(), optDate.get(), optTime.get());
          datesRepository.save(optDate.get());
        }
      }
      repository.deleteById(id);
    }
  }

  private void updateTimes(Reservation reservation, ReservationDates dates,
      ReservationTime reservationTime) {
    ReservationCutConfigEnum cutType = ReservationCutConfigEnum.valueOf(reservation.getCutTag());
    LocalTime parsedTime = LocalTime.parse(reservation.getTime());
    Long cutTime = cutType.getCutTime();
    Long plusMinutes = 0L;

    while (cutTime >= ReservationTimeConfigConstants.BASE_TIME) {
      setTimesAsActive(dates, parsedTime.plusMinutes(plusMinutes));
      plusMinutes += ReservationTimeConfigConstants.BASE_TIME;
      cutTime -= ReservationTimeConfigConstants.BASE_TIME;
    }
  }

  private void setTimesAsActive(ReservationDates dates, LocalTime time) {
    Optional<ReservationTime> optTime = dates.getAvailableTimes().stream()
        .filter(t -> t.getTime().equals(
            time.toString()))
        .findFirst();
    if (optTime.isPresent()) {
      optTime.get().setState(ReservationTimeConfigConstants.ACTIVE_STATE);
      optTime.get().setColor(ReservationTimeConfigConstants.ACTIVE_COLOR);
    }
  }
}
