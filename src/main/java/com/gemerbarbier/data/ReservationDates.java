package com.gemerbarbier.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(
    uniqueConstraints = { @UniqueConstraint( columnNames = {"date", "barber"} ) }
)
public class ReservationDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NonNull
    public String date;

    @NonNull
    public String barber;

    // @ElementCollection
    // @CollectionTable(name = "availableTimesList", 
    // uniqueConstraints = { @UniqueConstraint( columnNames = { "id", "time"} ) } , 
    // joinColumns = @JoinColumn(name = "id"))
    // @Column(name = "time")
    // private List<String> availableTimes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reservationDates_id")
    private List<ReservationTime> availableTimes;
}