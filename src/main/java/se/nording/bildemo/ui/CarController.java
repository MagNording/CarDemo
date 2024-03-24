package se.nording.bildemo.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.nording.bildemo.service.CarService;

@Controller
public class CarController {

    private final CarService cService;

    @Autowired
    public CarController(CarService cService) {
        this.cService = cService;
    }

    // form url
    @GetMapping("/form")
    public String formGet(Model m) {
        m.addAttribute("result", "");
        m.addAttribute("allcars", cService.getAllCars());
        return "formpage";
    }

    @PostMapping("/form")
    public String formPost(Model m,
                           @RequestParam("regnr") String regNr,
                           @RequestParam("model") String model,
                           @RequestParam("year") int year) {

        String resultString = cService.create(regNr, model, year);
        m.addAttribute("result", resultString);
        m.addAttribute("allcars", cService.getAllCars());
        return "formpage";
    }

    @GetMapping("/all")
    public String allCars(Model m) {
        m.addAttribute("allcars", cService.getAllCars());
        return "formpage";
    }

    @PostMapping("/removecarbymodel")
    public String removeCarByModel(@RequestParam String model, Model m) {
        m.addAttribute("removal", cService.removeCarsByModel(model));
        m.addAttribute("allcars", cService.getAllCars());
        return "formpage";
    }
}
