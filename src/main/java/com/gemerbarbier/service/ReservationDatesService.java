package com.gemerbarbier.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.gemerbarbier.config.ReservationDatesConfigConstats;
import com.gemerbarbier.config.ReservationTimeConfigConstats;
import com.gemerbarbier.data.ReservationDates;
import com.gemerbarbier.data.ReservationTime;
import com.gemerbarbier.repository.ReservationDatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationDatesService {

    @Autowired
    private ReservationDatesRepository repository;

    public List<ReservationDates> findReservationDates(String barber){
        List<ReservationDates> reservationDates = repository.findByBarber(barber);
        reservationDates.stream().forEach(r -> r.getAvailableTimes().sort(Comparator.comparing(ReservationTime::getTime)));
        return reservationDates;
    }

    public List<String> findAllAvaiableDates(String barber){
        return repository.findByBarber(barber).stream().map(ReservationDates::getDate).collect(Collectors.toList());
    }

    public List<String> findAvailableTimes(String date, String barber, String cutTag){
        Optional<ReservationDates> optDbDate = repository.findByDateAndBarber(date, barber);
        if(optDbDate.isPresent()){
            List<ReservationTime> times = optDbDate.get().getAvailableTimes();
            times = times.stream().filter(t->t.getState().equals(ReservationTimeConfigConstats.ACTIVE_STATE)).collect(Collectors.toList());
            
            switch (cutTag){
                case ReservationDatesConfigConstats.BASIC_CUT_TAG:
                    times = filterTimesForBasicCut(times);
                    break;
                case ReservationDatesConfigConstats.BASIC_BEARD_TAG:
                case ReservationDatesConfigConstats.EXCLUSIVE_CUT_TAG:
                    times = filterTimesForExclusiveCut(times);
                    break;
            }
            return times.stream().map(t->t.getTime()).sorted().collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public void createReservationDate(String date, String barber){
        ReservationDates dbDate = ReservationDates.builder().date(date).barber(barber).availableTimes(createTimes()).build();
        repository.save(dbDate);
    }

    public void deactiveTime(String date, String time, String barber){
        Optional<ReservationDates> optDbDate = repository.findByDateAndBarber(date, barber);
        if(optDbDate.isPresent()){
            List<ReservationTime> availableTimes = optDbDate.get().getAvailableTimes();
            setTimeAsInactive(availableTimes, time);
            repository.save(optDbDate.get());
            repository.flush();
        }
    }

    public void activateTime(String date, String time, String barber){
        Optional<ReservationDates> optDbDate = repository.findByDateAndBarber(date, barber);
        if(optDbDate.isPresent()){
            List<ReservationTime> availableTimes = optDbDate.get().getAvailableTimes();
            setTimeAsActive(availableTimes, time);
            repository.save(optDbDate.get());
            repository.flush();
        }
    }

    public void reserveTime(String date, String time, String barber, String cutTag){
        Optional<ReservationDates> optDbDate = repository.findByDateAndBarber(date, barber);
        if(optDbDate.isPresent()){
            List<ReservationTime> availableTimes = optDbDate.get().getAvailableTimes();
            setTimeAsReserved(availableTimes, time);

            LocalTime parsedTime = LocalTime.parse(time);

            switch (cutTag){
                case ReservationDatesConfigConstats.BASIC_CUT_TAG:
                    setTimeAsReserved(availableTimes, parsedTime.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString());
                    break;
                case ReservationDatesConfigConstats.BASIC_BEARD_TAG:
                case ReservationDatesConfigConstats.EXCLUSIVE_CUT_TAG:
                    setTimeAsReserved(availableTimes, parsedTime.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString());
                    setTimeAsReserved(availableTimes, parsedTime.plusMinutes(ReservationDatesConfigConstats.BASIC_CUT_TIME).toString());
                    break;
            }
            repository.save(optDbDate.get());
            repository.flush();
        }
    }

    private void setTimeAsActive(List<ReservationTime> availableTimes, String time){
        Optional<ReservationTime> optTimeToDeactive= availableTimes.stream().filter(t->t.getTime().equals(time)).findFirst();
        if( optTimeToDeactive.isPresent()){
            optTimeToDeactive.get().setState(ReservationTimeConfigConstats.ACTIVE_STATE);
            optTimeToDeactive.get().setColor(ReservationTimeConfigConstats.ACTIVE_COLOR);
        }
    }

    private void setTimeAsInactive(List<ReservationTime> availableTimes, String time){
        Optional<ReservationTime> optTimeToDeactive= availableTimes.stream().filter(t->t.getTime().equals(time)).findFirst();
        if( optTimeToDeactive.isPresent()){
            optTimeToDeactive.get().setState(ReservationTimeConfigConstats.INACTIVE_STATE);
            optTimeToDeactive.get().setColor(ReservationTimeConfigConstats.INACTIVE_COLOR);
        }
    }

    private void setTimeAsReserved(List<ReservationTime> availableTimes, String time){
        Optional<ReservationTime> optTimeToDeactive= availableTimes.stream().filter(t->t.getTime().equals(time)).findFirst();
        if( optTimeToDeactive.isPresent()){
            optTimeToDeactive.get().setState(ReservationTimeConfigConstats.RESERVED_STATE);
            optTimeToDeactive.get().setColor(ReservationTimeConfigConstats.RESERVED_COLOR);
        }
    }

    public void deleteDate(String date, String barber){
        Optional<ReservationDates> optDbDate = repository.findByDateAndBarber(date, barber);
        if(optDbDate.isPresent()){
            repository.delete(optDbDate.get());
        }
    }

    private List<ReservationTime> filterTimesForBasicCut(List<ReservationTime> times){
        List<ReservationTime> timesCp =  new ArrayList<>(times);
        List<ReservationTime> filteredTimes  = new ArrayList<>();
        for(ReservationTime t : times){
            Iterator<ReservationTime> timesIterator = timesCp.iterator();
            timesIterator.next();
            LocalTime time = LocalTime.parse(t.getTime());
            if(timesIterator.hasNext() && time.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString().equals(timesIterator.next().getTime())){
                filteredTimes.add(t);
            }
            timesCp.remove(t);
        }

        return filteredTimes;
    }

    private List<ReservationTime> filterTimesForExclusiveCut(List<ReservationTime> times){
        List<ReservationTime> timesCp =  new ArrayList<>(times);
        List<ReservationTime> filteredTimes  = new ArrayList<>();
        for(ReservationTime t : times){
            Iterator<ReservationTime> timesIterator = timesCp.iterator();
            timesIterator.next();
            LocalTime time = LocalTime.parse(t.getTime());
            if(timesIterator.hasNext() && time.plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString().equals(timesIterator.next().getTime())
                && timesIterator.hasNext() && time.plusMinutes(ReservationDatesConfigConstats.BASIC_CUT_TIME).toString().equals(timesIterator.next().getTime())){
                filteredTimes.add(t);
            }
            timesCp.remove(t);
        }

        return filteredTimes;
    }

   private List<ReservationTime> createTimes(){
        List<ReservationTime> times = new ArrayList<>();
        
        String initialTime = "10:00";
        while(!initialTime.equals("13:00")){
            times.add(ReservationTime.builder().state(ReservationTimeConfigConstats.ACTIVE_STATE).color(ReservationTimeConfigConstats.ACTIVE_COLOR).time(initialTime).build());
            initialTime = LocalTime.parse(initialTime).plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString();
        }
        initialTime = "14:00";
        while(!initialTime.equals("16:00")){
            times.add(ReservationTime.builder().state(ReservationTimeConfigConstats.ACTIVE_STATE).color(ReservationTimeConfigConstats.ACTIVE_COLOR).time(initialTime).build());
            initialTime = LocalTime.parse(initialTime).plusMinutes(ReservationDatesConfigConstats.BEARD_TIME).toString();
        }
        return times;
    } 
}
