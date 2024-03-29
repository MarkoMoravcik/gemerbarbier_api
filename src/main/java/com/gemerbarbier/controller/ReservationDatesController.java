package com.gemerbarbier.controller;

import com.gemerbarbier.data.ReservationDates;
import com.gemerbarbier.service.ReservationDatesService;
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
@RestController
@AllArgsConstructor
public class ReservationDatesController {

  private final ReservationDatesService service;

  @GetMapping("/reservationDates")
  public List<ReservationDates> getDates(
      @RequestParam(name = "barber") @NotNull String barber) {
    return service.findReservationDates(barber);
  }

  @GetMapping("/availableDates")
  public List<String> getAllAvailableDates(
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {
    return service.findAllAvaiableDates(barber, cutTag);
  }

  @GetMapping("/allDates")
  public List<String> getAllDates(
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {
    return service.findAllDates(barber, cutTag);
  }

  @GetMapping("/availableTimes")
  public List<String> getAllAvailableTimes(
      @RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {
    return service.findAvailableTimes(date, barber, cutTag);
  }

  @GetMapping("/allTimes")
  public List<String> getAllDates(
      @RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {
    return service.findAllTimes(date, barber, cutTag);
  }

  @PostMapping("/newFullDate")
  public void createNewFullDate(
      @RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.createReservationDate(date, barber);
  }

  @PostMapping("/deactiveTime")
  public void deactiveTime(@RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "time") @NotNull String time,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.deactiveTime(date, time, barber);
  }

  @PostMapping("/activateTime")
  public void activateTime(@RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "time") @NotNull String time,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.activateTime(date, time, barber);
  }

  @PostMapping("/activateInactiveTimes")
  public void activateTime(@RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.activateInactiveTimes(date, barber);
  }

  @PostMapping("/reserveTime")
  public void reserveTime(@RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "time") @NotNull String time,
      @RequestParam(name = "barber") @NotNull String barber,
      @RequestParam(name = "cutTag") @NotNull String cutTag) {
    service.reserveTime(date, time, barber, cutTag);
  }

  @DeleteMapping("/deleteDate")
  public void deleteDate(@RequestParam(name = "date") @NotNull String date,
      @RequestParam(name = "barber") @NotNull String barber) {
    service.deleteDate(date, barber);
  }
}
