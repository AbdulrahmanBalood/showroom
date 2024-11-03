package car.showroom.project.controller.Car;

import car.showroom.project.constants.RolesConstants;
import car.showroom.project.dto.Car.CarCreationDto;
import car.showroom.project.dto.Car.CarDto;
import car.showroom.project.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/car")
@RequiredArgsConstructor
public class CarPrivateController {
    private final CarService carService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarCreationDto carCreationDto) {
        return new ResponseEntity<>(carService.createCar(carCreationDto), HttpStatus.CREATED);
    }


}
