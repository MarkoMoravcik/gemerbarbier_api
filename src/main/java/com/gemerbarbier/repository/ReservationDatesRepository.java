package com.gemerbarbier.repository;

import java.util.List;
import java.util.Optional;

import com.gemerbarbier.data.ReservationDates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDatesRepository extends JpaRepository<ReservationDates, Long> {
    Optional<ReservationDates> findByDate(String date);

    Optional<ReservationDates> findByDateAndBarber(String date, String barber);

    List<ReservationDates> findByBarber(String barber);
}
