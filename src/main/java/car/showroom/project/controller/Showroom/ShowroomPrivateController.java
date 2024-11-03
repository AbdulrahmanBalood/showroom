package car.showroom.project.controller.Showroom;

import car.showroom.project.constants.RolesConstants;
import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.service.ShowroomService;
import car.showroom.project.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static car.showroom.project.constants.MessageConstants.SHOWROOM_DELETED_SUCCESSFULLY;

@RestController
@RequestMapping("/private/showroom")
@RequiredArgsConstructor
public class ShowroomPrivateController {
    private final ShowroomService showroomService;
    private final MessageUtils messageUtils;


    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    @PostMapping
    public ResponseEntity<ShowroomDto> createShowroom(@Valid @RequestBody ShowroomDto showroomDto) {
        return ResponseEntity.ok(showroomService.createShowroom(showroomDto));
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    public ResponseEntity<ShowroomDto> updateShowroom(@Valid @RequestBody ShowroomDto showroomDto) {
        return ResponseEntity.ok(showroomService.updateShowroom(showroomDto));
    }
    @DeleteMapping("/{crn}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('"+ RolesConstants.ROLE_ADMIN+"', '"+ RolesConstants.ROLE_USER+"')")
    public ResponseEntity<String> deleteShowroom(@PathVariable String crn) {
        showroomService.deleteShowroom(crn);
        return ResponseEntity.ok(messageUtils.getMessage(SHOWROOM_DELETED_SUCCESSFULLY));

    }
}
