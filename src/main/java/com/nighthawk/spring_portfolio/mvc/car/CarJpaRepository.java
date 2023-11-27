package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
    // You can add custom query methods if needed
}
