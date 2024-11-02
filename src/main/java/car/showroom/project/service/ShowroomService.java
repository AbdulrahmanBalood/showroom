package car.showroom.project.service;

import car.showroom.project.dto.CarDto;
import car.showroom.project.dto.ShowRoomPageDto;
import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.entitiy.Showroom;
import car.showroom.project.repository.ShowroomRepository;
import car.showroom.project.util.MessageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static car.showroom.project.constants.MessageConstants.SHOWROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShowroomService {
    private final ShowroomRepository showroomRepository;
    private final MessageUtils messageUtils;
    ModelMapper modelMapper = new ModelMapper();

    public Page<ShowRoomPageDto> retrieveAllShowrooms(Specification<Showroom> spec, Pageable pageable) {
        Page<Showroom> showrooms = showroomRepository.findAll(spec,pageable);
        return showrooms.map(showroom -> modelMapper.map(showroom, ShowRoomPageDto.class));
    }
    public ShowroomDto retrieveShowroomDtoByUuid(String crn) {
        Showroom showroom = retrieveShowroomByCRN(crn);
        return modelMapper.map(showroom, ShowroomDto.class);
    }
    public Showroom retrieveShowroomByUuid(String uuid){
        return showroomRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException(SHOWROOM_NOT_FOUND));
    }
    public Showroom retrieveShowroomByCRN(String crn){
        return showroomRepository.findByCommercialRegistrationNumber(crn).orElseThrow(() -> new RuntimeException(messageUtils.getMessage(SHOWROOM_NOT_FOUND)));
    }
    @Transactional
    public ShowroomDto createShowroom(ShowroomDto showroomDto) {
        Showroom showroom = modelMapper.map(showroomDto, Showroom.class);
        showroom = showroomRepository.save(showroom);
        return modelMapper.map(showroom, ShowroomDto.class);
    }
    @Transactional
    public ShowroomDto updateShowroom(ShowroomDto showroomDto) {
        Showroom showroom = retrieveShowroomByCRN(showroomDto.getCommercialRegistrationNumber());
        showroom.setManagerName(showroomDto.getManagerName());
        showroom.setAddress(showroomDto.getAddress());
        showroom.setPhoneNumber(showroomDto.getPhoneNumber());
        showroom = showroomRepository.save(showroom);
        return modelMapper.map(showroom, ShowroomDto.class);
    }
    @Transactional
    public void deleteShowroom(String crn) {
        Showroom showroom = retrieveShowroomByCRN(crn);
        showroomRepository.delete(showroom);
    }
}
