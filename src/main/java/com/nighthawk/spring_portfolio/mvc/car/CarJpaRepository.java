package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
    // Custom query method to find cars by carName
    List<Car> findByCarName(String carName);

    // Custom query method to find cars by sortingAlgorithmSpeed
    List<Car> findBySortingAlgorithmSpeed(int sortingAlgorithmSpeed);
}
