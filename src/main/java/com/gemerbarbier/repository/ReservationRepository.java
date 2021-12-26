package com.gemerbarbier.repository;

import java.util.List;
import java.util.Optional;

import com.gemerbarbier.data.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBarber(String barber);

    Optional<Reservation> findByIdAndBarber(Long id, String barber);

    void deleteByIdAndBarber(Long id, String barber);
}
