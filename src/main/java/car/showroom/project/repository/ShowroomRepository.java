package car.showroom.project.repository;

import car.showroom.project.entitiy.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowroomRepository extends JpaRepository<Showroom,Long> {
    Optional<Showroom> findByUuid(String uuid);
}
