package com.gemerbarbier.controller;

import com.gemerbarbier.data.Reservation;
import com.gemerbarbier.service.ReservationService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@AllArgsConstructor
@RestController
public class ReservationController {

  private final ReservationService service;

  @PostMapping("/reservation")
  public void createReservationAsUser(
      @RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "time") @NotNull String time,
      @RequestParam(name = "name") @NotNull String name,
      @RequestParam(name = "surname") @NotNull String surname,
      @RequestParam(name = "email") @NotNull String email,
      @RequestParam(name = "phoneNumber") @NotNull String phoneNumber,
      @RequestParam(name = "note") @NotNull String note,
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {

    service.createReservationAsUser(
        Reservation.builder().date(date).time(time).name(name).surname(surname).email(email)
            .phoneNumber(phoneNumber).note(note).barber(barber).cutTag(cutTag).build());
  }

  @PostMapping("/admin/reservation")
  public void createReservationAsAdmin(
      @RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "time") @NotNull String time,
      @RequestParam(name = "name") @NotNull String name,
      @RequestParam(name = "surname") @NotNull String surname,
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {

    service.createReservation(
        Reservation.builder().date(date).time(time).name(name).surname(surname).barber(barber)
            .cutTag(cutTag).build());
  }

  @GetMapping("/reservations")
  public List<Reservation> getAllReservations(
      @RequestParam(name = "barber") @NotNull String barber) {
    return service.getReservations(barber);
  }

  @DeleteMapping("/deleteReservation")
  public void deleteReservation(@RequestParam(name = "id") @NotNull Long id,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.deleteReservation(id, barber);
  }


}
