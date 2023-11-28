package com.nighthawk.spring_portfolio.mvc.car;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
