package se.nording.bildemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import se.nording.bildemo.data.CarRepository;

import java.util.List;

@Service
public class CarService {

    private final CarRepository cRepo;

    @Autowired
    public CarService(CarRepository cRepo) {
        this.cRepo = cRepo;
    }

    public String create(String regNo, String model, int year) {
        if (cRepo.existsById(regNo)) {
            return "Car already exists";
        } else {
            cRepo.save(new Car(regNo, model, year));
            return "Car saved successfully";
        }
    }

    public List<Car> getAllCars() {
        return cRepo.findAll();
    }

    public String removeCarsByModel(String model) {
        List<Car> cars = cRepo.findByModel(model);
        if (cars.isEmpty()) {
            return "No cars found with the model name: " + model + ", try again";
        } else {
            for (Car car : cars) {
                cRepo.deleteById(car.getRegNo());
            }
            return cars.size() + " car(s) with model name " + model + " have been successfully removed";
        }
    }

}
