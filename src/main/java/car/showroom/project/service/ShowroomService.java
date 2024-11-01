package car.showroom.project.service;

import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.entitiy.Showroom;
import car.showroom.project.repository.ShowroomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowroomService {
    private final ShowroomRepository showroomRepository;
    ModelMapper modelMapper = new ModelMapper();

    public List<ShowroomDto> retrieveAllShowrooms() {
        List<Showroom> showrooms = showroomRepository.findAll();
        return showrooms.stream().map(showroom -> modelMapper.map(showroom, ShowroomDto.class)).collect(Collectors.toList());
    }
    public ShowroomDto retrieveShowroomByUuid(String uuid) {
        //handle errors
        Showroom showroom = showroomRepository.findByUuid(uuid).get();
        return modelMapper.map(showroom, ShowroomDto.class);
    }
    public ShowroomDto createShowroom(ShowroomDto showroomDto) {
        Showroom showroom = modelMapper.map(showroomDto, Showroom.class);
        showroom = showroomRepository.save(showroom);
        return modelMapper.map(showroom, ShowroomDto.class);
    }
}
