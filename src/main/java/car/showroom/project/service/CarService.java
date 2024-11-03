package car.showroom.project.service;

import car.showroom.project.dto.Car.CarCreationDto;
import car.showroom.project.dto.Car.CarDto;
import car.showroom.project.dto.Car.CarPageDto;
import car.showroom.project.entitiy.CarEntity;
import car.showroom.project.repository.CarRepository;
import car.showroom.project.util.SessionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    ModelMapper modelMapper = new ModelMapper();
    public final ShowroomService showroomService;
    @Transactional
    public CarDto createCar(CarCreationDto carDto) {
        CarEntity carEntity = new CarEntity();
        carEntity.setModel(carDto.getModel());
        carEntity.setCreatedBy(SessionUtils.getCurrentUser());
        carEntity.setMaker(carDto.getMaker());
        carEntity.setModelYear(carDto.getModelYear());
        carEntity.setPrice(carDto.getPrice());
        carEntity.setVin(carDto.getVin());
        carEntity.setShowroomEntity(showroomService.retrieveShowroomByCRN(carDto.getShowroomCrn()));
        carEntity = carRepository.save(carEntity);
        return modelMapper.map(carEntity,CarDto.class);
    }

    public Page<CarPageDto> retrieveAllCars(Specification<CarEntity> spec, Pageable pageable) {
        Page<CarEntity> cars = carRepository.findAll(spec, pageable);
        return cars.map(car -> {
            CarPageDto carPageDto = modelMapper.map(car, CarPageDto.class);
            carPageDto.setShowroomName(car.getShowroomEntity().getName());
            carPageDto.setShowroomPhoneNumber(car.getShowroomEntity().getPhoneNumber());
            return carPageDto;
        });
    }
}
