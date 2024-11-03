package car.showroom.project.controller.Car;

import car.showroom.project.dto.Car.CarDto;
import car.showroom.project.dto.Car.CarPageDto;
import car.showroom.project.entitiy.CarEntity;
import car.showroom.project.service.CarService;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CarPageDto> retrieveAllCars(@Conjunction(value = {
            @Or({
                    @Spec(path = "maker", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "model", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "vin", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "modelYear", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "showroomEntity.name", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "showroomEntity.phoneNumber", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "showroomEntity.commercialRegistrationNumber", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "showroomEntity.address", params = "search", spec = LikeIgnoreCase.class)
            }),

    })Specification<CarEntity> spec, Pageable pageable) {
        return carService.retrieveAllCars(spec,pageable);
    }




}
