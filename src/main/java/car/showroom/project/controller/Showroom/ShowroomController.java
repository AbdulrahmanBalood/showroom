package car.showroom.project.controller.Showroom;

import car.showroom.project.dto.Showroom.ShowRoomPageDto;
import car.showroom.project.dto.Showroom.ShowroomDto;
import car.showroom.project.entitiy.ShowroomEntity;
import car.showroom.project.service.ShowroomService;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/showroom")
@RequiredArgsConstructor
public class ShowroomController {
    private final ShowroomService showroomService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<ShowRoomPageDto>> retrieveAllShowrooms(@Conjunction({
            @Or({
                    @Spec(path = "name", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "address", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "commercialRegistrationNumber", params = "search", spec = Like.class),
                    @Spec(path = "phoneNumber", params = "search", spec = Like.class),

            }),
    }) Specification<ShowroomEntity> spec, Pageable pageable) {
        return ResponseEntity.ok(showroomService.retrieveAllShowrooms(spec, pageable));
    }
    @GetMapping("/{crn}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShowroomDto> retrieveShowroomByUuid(@PathVariable String crn) {
        return ResponseEntity.ok(showroomService.retrieveShowroomDtoByUuid(crn));

    }

}
