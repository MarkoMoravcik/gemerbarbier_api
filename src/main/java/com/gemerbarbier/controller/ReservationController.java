package com.gemerbarbier.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.gemerbarbier.data.Reservation;
import com.gemerbarbier.repository.ReservationRepository;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReservationController {
    private ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/reservation")
    public void createReservation(
    @RequestParam(name = "date", required = true) @NotNull String date,
    @RequestParam(name = "time", required = true) @NotNull String time,
    @RequestParam(name = "name", required = true) @NotNull String name,
    @RequestParam(name = "surname", required = true) @NotNull String surname,
    @RequestParam(name = "email", required = true) @NotNull String email,
    @RequestParam(name = "phoneNumber", required = true) @NotNull String phoneNumber,
    @RequestParam(name = "note", required = true) @NotNull String note,
    @RequestParam(name = "barber", required = true) @NotNull String barber,
    @RequestParam(name = "cutType", required = true) @NotNull String cutType,
    @RequestParam(name = "cutTag", required = true) @NotNull String cutTag
    ) {
        Reservation reservation = Reservation.builder().date(date).time(time).name(name).
        surname(surname).email(email).phoneNumber(phoneNumber).note(note).barber(barber).cutType(cutType).cutTag(cutTag).build();
        
         repository.save(reservation); 
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations(
        @RequestParam(name = "barber", required = true) @NotNull String barber
    ) {
         return repository.findByBarber(barber);
    }
}