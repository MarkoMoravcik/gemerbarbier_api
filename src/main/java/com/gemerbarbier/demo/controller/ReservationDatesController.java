package com.gemerbarbier.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.gemerbarbier.demo.data.ReservationDates;
import com.gemerbarbier.demo.repository.ReservationDatesRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReservationDatesController {

    @Autowired
    private ReservationDatesRepository repository;

    public ReservationDatesController(ReservationDatesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reservationDates")
    public List<ReservationDates> getDates() {
        return repository.findAll();
    }

    @GetMapping("/availableDates")
    public List<String> getAllAvailableDates() {
        return repository.findAll().stream().map(ReservationDates::getDate).collect(Collectors.toList());
    }

    @GetMapping("/availableTimes")
    public List<String> getAllAvailableTimes(
        @RequestParam(name = "date", required = true) @NotNull String date) {
        return repository.findByDate(date).getAvailableTimes();
    }

    @PostMapping("/newFullDate")
    public void createNewFullDate(
        @RequestParam(name = "date", required = true) @NotNull String date) {
        ReservationDates dbDate = ReservationDates.builder().date(date).availableTimes(createTimes()).build();
        repository.save(dbDate);
    }

    @PostMapping("/newDate")
    public void createNewDate(
        @RequestParam(name = "date", required = true) @NotNull String date,
        @RequestParam(name = "time", required = true) @NotNull String time) {
        ReservationDates dbDate = ReservationDates.builder().date(date).build();
        dbDate.getAvailableTimes().add(time);
        repository.save(dbDate);
    }

    @DeleteMapping("/deleteTime")
    public void deleteTime(
        @RequestParam(name = "date", required = true) @NotNull String date,
        @RequestParam(name = "time", required = true) @NotNull String time) {
        ReservationDates dbDate = repository.findByDate(date);
        List<String> availableTimes = dbDate.getAvailableTimes();
        availableTimes.remove(time);
        if (availableTimes.isEmpty()){
            repository.delete(dbDate);
        }
        repository.save(dbDate);
    }

    private List<String> createTimes(){
        List<String> times = new ArrayList<>();
        times.add("09:00");
        times.add("09:30");
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");
        times.add("13:00");
        times.add("13:30");
        times.add("14:00");
        times.add("14:30");
        times.add("15:00");
        times.add("15:30");
        times.add("16:00");
        times.add("16:30");
        times.add("17:00");
        times.add("17:30");
        times.add("18:00");
        return times;
    }
}