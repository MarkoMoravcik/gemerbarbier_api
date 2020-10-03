package com.gemerbarbier.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.gemerbarbier.data.ReservationDates;
import com.gemerbarbier.repository.ReservationDatesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationDatesService {

    @Autowired
    private ReservationDatesRepository repository;

    public List<ReservationDates>  findReservationDates(String barber){
        return repository.findByBarber(barber);
    }

    public List<String> findAllAvaiableDates(String barber){
        return repository.findByBarber(barber).stream().map(ReservationDates::getDate).collect(Collectors.toList());
    }

    public List<String> findAvailableTimes(String date, String barber, String cutTime){
        List<String> times = repository.findByDateAndBarber(date, barber).getAvailableTimes();
        if(cutTime.equals("40")){
            times = filterTimesFor40Minutes(times);
        }else if (cutTime.equals("60")){
            times = filterTimesFor60Minutes(times);
        }
        return times;
    }

    public void createReservationDate(String date, String barber){
        ReservationDates dbDate = ReservationDates.builder().date(date).barber(barber).availableTimes(createTimes()).build();
        repository.save(dbDate);
    }

    public void deleteTime(String date, String time, String barber, String cutTag){
        ReservationDates dbDate = repository.findByDateAndBarber(date, barber);
        List<String> availableTimes = dbDate.getAvailableTimes();

        availableTimes.remove(time);
        LocalTime parsedTime = LocalTime.parse(time);

        if(cutTag.equals("BASIC_CUT")){
            availableTimes.remove(parsedTime.plusMinutes(20).toString());
        } else if (cutTag.equals("BASIC_BEARD") || cutTag.equals("EXCLUSIVE_CUT")){
            availableTimes.remove(parsedTime.plusMinutes(40).toString());
        }

        if (availableTimes.isEmpty()){
            repository.delete(dbDate);
        }
        repository.flush();
    }

    public void deleteDate(String date, String barber){
        ReservationDates dbDate = repository.findByDateAndBarber(date, barber);
        repository.delete(dbDate);
    }

    private List<String> filterTimesFor40Minutes(List<String> times){
        List<String> timesCp =  new ArrayList<>(times);
        List<String> filteredTimes  = new ArrayList<>();
        for(String t : times){
            Iterator<String> timesIterator = timesCp.iterator();
            timesIterator.next();
            LocalTime time = LocalTime.parse(t);
            if(timesIterator.hasNext() && time.plusMinutes(20).toString().equals(timesIterator.next()) && timesIterator.hasNext() &&time.plusMinutes(40).toString().equals(timesIterator.next())){
                filteredTimes.add(t);
            }
            timesCp.remove(t);
        }

        return filteredTimes;
    }

    private List<String> filterTimesFor60Minutes(List<String> times){
        List<String> timesCp =  new ArrayList<>(times);
        List<String> filteredTimes  = new ArrayList<>();
        for(String t : times){
            Iterator<String> timesIterator = timesCp.iterator();
            timesIterator.next();
            LocalTime time = LocalTime.parse(t);
            if(timesIterator.hasNext() && time.plusMinutes(20).toString().equals(timesIterator.next())&& timesIterator.hasNext() && time.plusMinutes(40).toString().equals(timesIterator.next()) && timesIterator.hasNext() && time.plusMinutes(60).toString().equals(timesIterator.next())){
                filteredTimes.add(t);
            }
            timesCp.remove(t);
        }

        return filteredTimes;
    }

   private List<String> createTimes(){
        List<String> times = new ArrayList<>();
        // times.add("09:00");
        // times.add("09:30");
        times.add("10:00");
        times.add("10:20");
        times.add("10:40");
        times.add("11:00");
        times.add("11:20");
        times.add("11:40");
        times.add("12:00");
        times.add("12:20");
        times.add("12:40");
        //times.add("13:00");
        //times.add("13:30");
        times.add("14:00");
        times.add("14:20");
        times.add("14:40");
        times.add("15:00");
        times.add("15:20");
        times.add("15:40");
        times.add("16:00");
        // times.add("16:30");
        // times.add("17:00");
        // times.add("17:30");
        // times.add("18:00");
        return times;
    } 
}
