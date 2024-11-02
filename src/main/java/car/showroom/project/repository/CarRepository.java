package car.showroom.project.repository;

import car.showroom.project.entitiy.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long>, JpaSpecificationExecutor<Car> {
    Optional<Car> findByUuid(String uuid);
}
