package car.showroom.project.controller;

import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.service.ShowroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private/showroom")
@RequiredArgsConstructor
public class ShowroomController {
    private final ShowroomService showroomService;

    @GetMapping
    public List<ShowroomDto> retrieveAllShowrooms() {
        return showroomService.retrieveAllShowrooms();
    }
    @PostMapping
    public ShowroomDto createShowroom(@Valid @RequestBody ShowroomDto showroomDto) {
        return showroomService.createShowroom(showroomDto);
    }
}
