package com.nighthawk.spring_portfolio.mvc.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String carName;

    @Column
    private int sortingAlgorithmSpeed;

    public Car(String carName, int sortingAlgorithmSpeed) {
        this.carName = carName;
        this.sortingAlgorithmSpeed = sortingAlgorithmSpeed;
    }

}
