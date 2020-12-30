package com.gemerbarbier.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.gemerbarbier.config.ReservationDatesConfigConstats;
import com.gemerbarbier.config.ReservationTimeConfigConstats;
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
    
    public void createReservation(Reservation reservation){
        
        LocalTime parsedTime = LocalTime.parse(reservation.getTime());
        String parsedDate = LocalDate.parse(reservation.getDate()).toString();
        reservation.setStartDateTime(createDateTime(parsedDate, parsedTime, 0L));

        switch (reservation.getCutTag()) {
            case ReservationDatesConfigConstats.BEARD_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BEARD_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BEARD_COLOR);
              reservation.setCutType(ReservationDatesConfigConstats.BEARD_NAME);
              break;
            case ReservationDatesConfigConstats.BASIC_CUT_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BASIC_CUT_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BASIC_CUT_COLOR);
              reservation.setCutType(ReservationDatesConfigConstats.BASIC_CUT_NAME);
              break;
            case ReservationDatesConfigConstats.BASIC_BEARD_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.BASIC_BEARD_TIME));
              reservation.setColor(ReservationDatesConfigConstats.BASIC_BEARD_COLOR);
              reservation.setCutType(ReservationDatesConfigConstats.BASIC_BEARD_NAME);
              break;
            case ReservationDatesConfigConstats.EXCLUSIVE_CUT_TAG:
              reservation.setEndDateTime(createDateTime(parsedDate, parsedTime, ReservationDatesConfigConstats.EXCLUSIVE_CUT_TIME));
              reservation.setColor(ReservationDatesConfigConstats.EXCLUSIVE_CUT_COLOR);
              reservation.setCutType(ReservationDatesConfigConstats.EXCLUSIVE_CUT_NAME);
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

	public void deleteReservation(@NotNull Long id, @NotNull String barber) {
    Optional<Reservation> optReservation = repository.findByIdAndBarber(id, barber);
    if(optReservation.isPresent()){
      Optional<ReservationDates> optDate = datesRepository.findByDateAndBarber(optReservation.get().getDate(), barber);
        if(optDate.isPresent()){
          Optional<ReservationTime> optTime = optDate.get().getAvailableTimes().stream().filter(t->t.getTime().equals(optReservation.get().getTime())).findFirst();
          if(optTime.isPresent()){
            updateTimes(optReservation.get(),optDate.get(), optTime.get());
            datesRepository.save(optDate.get()); 
            repository.deleteById(id);
          }
        }
    }
  }
  
  private void updateTimes(Reservation reservation,ReservationDates dates, ReservationTime reservationTime){
    reservationTime.setState(ReservationTimeConfigConstats.ACTIVE_STATE);
    reservationTime.setColor(ReservationTimeConfigConstats.ACTIVE_COLOR);
    LocalTime localTime = LocalTime.parse(reservation.getTime());
    switch (reservation.getCutTag()){
      case ReservationDatesConfigConstats.BASIC_CUT_TAG:
        ReservationTime time =  dates.getAvailableTimes().stream().filter(t -> t.getTime().equals(localTime.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString())).findFirst().get();
        time.setState(ReservationTimeConfigConstats.ACTIVE_STATE);
        time.setColor(ReservationTimeConfigConstats.ACTIVE_COLOR);
        break;
      case ReservationDatesConfigConstats.BASIC_BEARD_TAG:
        dates.getAvailableTimes().stream().filter(t->t.getTime().equals(localTime.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString()) || t.getTime().equals(localTime.plusMinutes(ReservationDatesConfigConstats.BASIC_CUT_TIME).toString()))
        .forEach(r -> {
          r.setState(ReservationTimeConfigConstats.ACTIVE_STATE);
          r.setColor(ReservationTimeConfigConstats.ACTIVE_COLOR);
        });
        break;
  }
  }
}
