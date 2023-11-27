package com.nighthawk.spring_portfolio.mvc;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.car.Car;
import com.nighthawk.spring_portfolio.mvc.car.CarJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
public class ModelInit {
    @Autowired
    private PersonDetailsService personService;

    @Autowired
    private CarJpaRepository carRepository;

    @Bean
    CommandLineRunner run() {
        return args -> {
            // Person database is populated with test data
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                List<Person> personFound = personService.list(person.getName(), person.getEmail());
                if (personFound.size() == 0) {
                    personService.save(person);
                }
            }

            // Car database is populated with test data
            Car[] carArray = Car.init(); // Assuming you have a static method init() in your Car class
            for (Car car : carArray) {
                Car existingCar = carRepository.save(car);
                if (existingCar != null) {
                    // Handle successfully saved car
                }
            }
        };
    }
}
