package com.gemerbarbier.demo.repository;

import com.gemerbarbier.demo.data.ReservationDates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReservationDatesRepository extends JpaRepository<ReservationDates, Long> {
    ReservationDates findByDate(String date);
}