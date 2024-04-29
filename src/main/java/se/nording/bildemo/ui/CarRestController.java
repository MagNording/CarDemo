package se.nording.bildemo.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.nording.bildemo.service.Car;
import se.nording.bildemo.service.CarService;

import java.util.Optional;

@RestController
public class CarRestController {

    private final CarService service;

    public CarRestController(CarService service) {
        this.service = service;
    }

    @GetMapping("/rs/car/{rn}")
    public ResponseEntity<Car> getCar(@PathVariable("rn") String regNo) {
        Optional<Car> car = service.getCar(regNo);
        return car.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @PostMapping("/rs/car")
    public String regCar(@RequestBody Car car) {
        return service.create(car.getRegNo(), car.getModel(), car.getYear());
    }
}
