package com.nighthawk.spring_portfolio.mvc.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarApiController {

    @Autowired
    private CarJpaRepository carRepository;

    @GetMapping("/")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/post")
    public ResponseEntity<Car> postCar(@PathVariable int length) {
        if (length <= 0) {
            // Handle invalid input
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Call the existing utility method to generate a random array
        int[] randomArray = CarSortingAlgorithm.generateRandomArray(length);

        // Create a Car object with the generated array
        Car car = new Car(randomArray);

        // You can perform additional logic here if needed

        // Return the Car object in the response
        return new ResponseEntity<>(car, HttpStatus.OK);
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        return carRepository.findById(id)
                .map(existingCar -> {
                    existingCar.setCarName(updatedCar.getCarName());
                    existingCar.setSortingAlgorithmSpeed(updatedCar.getSortingAlgorithmSpeed());
                    // Update other attributes as needed

                    Car savedCar = carRepository.save(existingCar);
                    return new ResponseEntity<>(savedCar, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
