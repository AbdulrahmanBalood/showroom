package car.showroom.project.service;

import car.showroom.project.constants.MessageConstants;
import car.showroom.project.dto.CarDto;
import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.entitiy.Car;
import car.showroom.project.exceptions.BusinessValidationException;
import car.showroom.project.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static car.showroom.project.constants.MessageConstants.CAR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    ModelMapper modelMapper = new ModelMapper();
    public final ShowroomService showroomService;
    @Transactional
    public CarDto createCar(CarDto carDto) {
        Car car = new Car();
        car.setModel(carDto.getModel());
        car.setMaker(carDto.getMaker());
        car.setModelYear(carDto.getModelYear());
        car.setPrice(carDto.getPrice());
        car.setVin(carDto.getVin());
        car.setShowroom(showroomService.retrieveShowroomByUuid(carDto.getShowroom().getUuid()));
        car = carRepository.save(car);
        return carDto;
    }

    public Page<CarDto> retrieveAllCars(Specification<Car> spec, Pageable pageable) {
        Page<Car> cars = carRepository.findAll(spec,pageable);
        return cars.map(car -> modelMapper.map(car, CarDto.class));
    }
}
