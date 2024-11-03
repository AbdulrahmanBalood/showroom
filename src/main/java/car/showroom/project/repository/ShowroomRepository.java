package car.showroom.project.repository;

import car.showroom.project.entitiy.ShowroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ShowroomRepository extends JpaRepository<ShowroomEntity,Long>, JpaSpecificationExecutor {
    Optional<ShowroomEntity> findByUuid(String uuid);
    Optional<ShowroomEntity> findByCommercialRegistrationNumber(String crn);
}
