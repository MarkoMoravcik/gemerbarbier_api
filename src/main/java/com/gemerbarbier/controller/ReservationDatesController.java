package com.gemerbarbier.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.gemerbarbier.data.ReservationDates;
import com.gemerbarbier.service.ReservationDatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReservationDatesController {

    @Autowired
    private ReservationDatesService service;

    @GetMapping("/reservationDates")
    public List<ReservationDates> getDates(
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        return service.findReservationDates(barber);
    }

    @GetMapping("/availableDates")
    public List<String> getAllAvailableDates(
            @RequestParam(name = "barber", required = true) @NotNull String barber,
            @RequestParam(name = "cutTag", required = true) @NotNull String cutTag) {
        return service.findAllAvaiableDates(barber, cutTag);
    }

    @GetMapping("/allDates")
    public List<String> getAllDates(
            @RequestParam(name = "barber", required = true) @NotNull String barber,
            @RequestParam(name = "cutTag", required = true) @NotNull String cutTag) {
        return service.findAllDates(barber, cutTag);
    }

    @GetMapping("/availableTimes")
    public List<String> getAllAvailableTimes(
            @RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "barber", required = true) @NotNull String barber,
            @RequestParam(name = "cutTag", required = true) @NotNull String cutTag) {
        return service.findAvailableTimes(date, barber, cutTag);
    }

    @GetMapping("/allTimes")
    public List<String> getAllDates(
            @RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "barber", required = true) @NotNull String barber,
            @RequestParam(name = "cutTag", required = true) @NotNull String cutTag) {
        return service.findAllTimes(date, barber, cutTag);
    }

    @PostMapping("/newFullDate")
    public void createNewFullDate(
            @RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        service.createReservationDate(date, barber);
    }

    @PostMapping("/deactiveTime")
    public void deactiveTime(@RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "time", required = true) @NotNull String time,
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        service.deactiveTime(date, time, barber);
    }

    @PostMapping("/activateTime")
    public void activateTime(@RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "time", required = true) @NotNull String time,
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        service.activateTime(date, time, barber);
    }

    @PostMapping("/activateInactiveTimes")
    public void activateTime(@RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        service.activateInactiveTimes(date, barber);
    }

    @PostMapping("/reserveTime")
    public void reserveTime(@RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "time", required = true) @NotNull String time,
            @RequestParam(name = "barber", required = true) @NotNull String barber,
            @RequestParam(name = "cutTag", required = true) @NotNull String cutTag) {
        service.reserveTime(date, time, barber, cutTag);
    }

    @DeleteMapping("/deleteDate")
    public void deleteDate(@RequestParam(name = "date", required = true) @NotNull String date,
            @RequestParam(name = "barber", required = true) @NotNull String barber) {
        service.deleteDate(date, barber);
    }
}
