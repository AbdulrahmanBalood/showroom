package car.showroom.project.repository;

import car.showroom.project.entitiy.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
