package com.gemerbarbier.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.gemerbarbier.config.ReservationDatesConfigConstats;
import com.gemerbarbier.data.Reservation;
import com.gemerbarbier.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository repository;
    
    public void createReservation(Reservation reservation){
        
        LocalTime parsedTime = LocalTime.parse(reservation.getTime());
        String parsedDate = LocalDate.parse(reservation.getDate()).toString();
        reservation.setStartDateTime(createDateTime(parsedDate, parsedTime, 0L));

        switch (reservation.getCutTag()) {
            case ReservationDatesConfigConstats.BEARD_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BEARD_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BEARD_COLOR);
              break;
            case ReservationDatesConfigConstats.BASIC_CUT_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BASIC_CUT_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BASIC_CUT_COLOR);
              break;
            case ReservationDatesConfigConstats.BASIC_BEARD_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BASIC_BEARD_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BASIC_BEARD_COLOR);
              break;
            case ReservationDatesConfigConstats.EXCLUSIVE_CUT_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.EXCLUSIVE_CUT_TIME));
              reservation.setColor(ReservationDatesConfigConstats.EXCLUSIVE_CUT_COLOR);
              break;
          }

         repository.save(reservation); 
    }

    public List<Reservation> getReservations(String barber){
      return repository.findByBarber(barber);
    }

    private String createDateTime(String parsedDate, LocalTime parsedTime, Long plusMinutes){
        return parsedDate + " " + parsedTime.plusMinutes(plusMinutes).toString();
    }
}
