package com.gemerbarbier.demo.data;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ElementCollection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ReservationDates {

    @Id
    @GeneratedValue
    public Long id;

    @Basic
    public String date;

    @ElementCollection
    @CollectionTable(name = "availableTimesList", 
    uniqueConstraints = { @UniqueConstraint( columnNames = { "id", "time" } ) } , 
    joinColumns = @JoinColumn(name = "id"))
    @Column(name = "time")
    private List<String> availableTimes;

}