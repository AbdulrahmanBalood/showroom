package car.showroom.project.repository;

import car.showroom.project.entitiy.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ShowroomRepository extends JpaRepository<Showroom,Long>, JpaSpecificationExecutor {
    Optional<Showroom> findByUuid(String uuid);
    Optional<Showroom> findByCommercialRegistrationNumber(String crn);
}
