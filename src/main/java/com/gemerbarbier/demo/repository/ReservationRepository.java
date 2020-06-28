package com.gemerbarbier.demo.repository;

import com.gemerbarbier.demo.data.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}