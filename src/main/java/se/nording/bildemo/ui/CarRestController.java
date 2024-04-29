package se.nording.bildemo.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
}
