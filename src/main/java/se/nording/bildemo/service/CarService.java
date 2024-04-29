package se.nording.bildemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import se.nording.bildemo.data.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository cRepo;

    @Autowired // Dependency injection
    public CarService(CarRepository cRepo) {
        this.cRepo = cRepo;
    }

    // Skapa en ny bil
    public String create(String regNo, String model, int year) {
        // Kolla om bilen redan finns med regnr (trådsäkert)
        synchronized (this) {
            if (cRepo.existsById(regNo)) {
                return "Car already exists";
            } else {
                cRepo.save(new Car(regNo, model, year));
                return "Car saved successfully";
            }
        }
    }

    // Hämta alla bilar
    public List<Car> getAllCars() {
        return cRepo.findAll();
    }

    // Ta bort bilar av en viss modell
    public String removeCarsByModel(String model) {
        List<Car> cars = cRepo.findByModel(model);
        // Om bil arrayen är tom
        if (cars.isEmpty()) {
            return "No cars found with the model name: " + model + ", try again";
        } else {
            for (Car car : cars) {
                cRepo.deleteById(car.getRegNo());
            }
            return cars.size() + " car(s) with model name " + model +
                    " have been successfully removed";
        }
    }

    public Optional<Car> getCar(String regNo) {
        return cRepo.findById(regNo);
    }
}
