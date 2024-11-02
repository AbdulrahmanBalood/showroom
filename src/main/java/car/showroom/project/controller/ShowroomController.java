package car.showroom.project.controller;

import car.showroom.project.dto.ShowRoomPageDto;
import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.entitiy.Car;
import car.showroom.project.entitiy.Showroom;
import car.showroom.project.service.ShowroomService;
import car.showroom.project.util.MessageUtils;
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

import java.util.List;

import static car.showroom.project.constants.MessageConstants.SHOWROOM_DELETED_SUCCESSFULLY;

@RestController
@RequestMapping("/private/showroom")
@RequiredArgsConstructor
public class ShowroomController {
    private final ShowroomService showroomService;
    private final MessageUtils messageUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Page<ShowRoomPageDto>> retrieveAllShowrooms(@Conjunction({
            @Or({
                    @Spec(path = "name", params = "name", spec = Like.class),

            }),
            @Or({
                    @Spec(path = "commercialRegistrationNumber", params = "crn", spec = Equal.class),

            }),
            @Or({
                    @Spec(path = "phoneNumber", params = "phoneNumber", spec = Equal.class),

            }),
            @Or({
                    @Spec(path = "address", params = "address", spec = Like.class),

            }),
    }) Specification<Showroom> spec, Pageable pageable) {
        return ResponseEntity.ok(showroomService.retrieveAllShowrooms(spec, pageable));
    }
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ShowroomDto> retrieveShowroomByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(showroomService.retrieveShowroomDtoByUuid(uuid));

    }
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ShowroomDto> createShowroom(@Valid @RequestBody ShowroomDto showroomDto) {
        return ResponseEntity.ok(showroomService.createShowroom(showroomDto));
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ShowroomDto> updateShowroom(@Valid @RequestBody ShowroomDto showroomDto) {
        return ResponseEntity.ok(showroomService.updateShowroom(showroomDto));
    }
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteShowroom(@PathVariable String uuid) {
        showroomService.deleteShowroom(uuid);
        return ResponseEntity.ok(messageUtils.getMessage(SHOWROOM_DELETED_SUCCESSFULLY));

    }
}
