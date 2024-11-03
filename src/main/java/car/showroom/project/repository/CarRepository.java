package car.showroom.project.repository;

import car.showroom.project.entitiy.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity,Long>, JpaSpecificationExecutor<CarEntity> {
    Optional<CarEntity> findByUuid(String uuid);
}
