package car.showroom.project.controller;

import car.showroom.project.constants.RolesConstants;
import car.showroom.project.dto.CarCreationDto;
import car.showroom.project.dto.CarDto;
import car.showroom.project.entitiy.Car;
import car.showroom.project.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    public Page<CarDto> retrieveAllCars(@Conjunction(value = {
            @Or({
                    @Spec(path = "brand", params = "brand", spec = Like.class),

            }),
            @Or({
                    @Spec(path = "model", params = "model", spec = Like.class),

            }),
            @Or({
                    @Spec(path = "name", params = "name", spec = Like.class),

            }),
            @Or({
                    @Spec(path = "year", params = "year", spec = Equal.class),

            }),
            @Or({
                    @Spec(path = "price", params = "price", spec = Equal.class),

            }),
            @Or({
                    @Spec(path = "showroom.name", params = "showroom", spec = Equal.class)

            })
    })Specification<Car> spec, Pageable pageable) {
        return carService.retrieveAllCars(spec,pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarCreationDto carCreationDto) {
        return new ResponseEntity<>(carService.createCar(carCreationDto), HttpStatus.CREATED);
    }


}
