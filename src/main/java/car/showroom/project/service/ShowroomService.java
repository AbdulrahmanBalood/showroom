package car.showroom.project.service;

import car.showroom.project.dto.ShowRoomPageDto;
import car.showroom.project.dto.ShowroomDto;
import car.showroom.project.entitiy.ShowroomEntity;
import car.showroom.project.repository.ShowroomRepository;
import car.showroom.project.util.MessageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static car.showroom.project.constants.MessageConstants.SHOWROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShowroomService {
    private final ShowroomRepository showroomRepository;
    private final MessageUtils messageUtils;
    ModelMapper modelMapper = new ModelMapper();

    public Page<ShowRoomPageDto> retrieveAllShowrooms(Specification<ShowroomEntity> spec, Pageable pageable) {
        Page<ShowroomEntity> showrooms = showroomRepository.findAll(spec,pageable);
        return showrooms.map(showroom -> modelMapper.map(showroom, ShowRoomPageDto.class));
    }
    public ShowroomDto retrieveShowroomDtoByUuid(String crn) {
        ShowroomEntity showroomEntity = retrieveShowroomByCRN(crn);
        return modelMapper.map(showroomEntity, ShowroomDto.class);
    }
    public ShowroomEntity retrieveShowroomByUuid(String uuid){
        return showroomRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException(SHOWROOM_NOT_FOUND));
    }
    public ShowroomEntity retrieveShowroomByCRN(String crn){
        return showroomRepository.findByCommercialRegistrationNumber(crn).orElseThrow(() -> new RuntimeException(messageUtils.getMessage(SHOWROOM_NOT_FOUND)));
    }
    @Transactional
    public ShowroomDto createShowroom(ShowroomDto showroomDto) {
        ShowroomEntity showroomEntity = modelMapper.map(showroomDto, ShowroomEntity.class);
        showroomEntity = showroomRepository.save(showroomEntity);
        return modelMapper.map(showroomEntity, ShowroomDto.class);
    }
    @Transactional
    public ShowroomDto updateShowroom(ShowroomDto showroomDto) {
        ShowroomEntity showroomEntity = retrieveShowroomByCRN(showroomDto.getCommercialRegistrationNumber());
        showroomEntity.setManagerName(showroomDto.getManagerName());
        showroomEntity.setAddress(showroomDto.getAddress());
        showroomEntity.setPhoneNumber(showroomDto.getPhoneNumber());
        showroomEntity = showroomRepository.save(showroomEntity);
        return modelMapper.map(showroomEntity, ShowroomDto.class);
    }
    @Transactional
    public void deleteShowroom(String crn) {
        ShowroomEntity showroomEntity = retrieveShowroomByCRN(crn);
        showroomRepository.delete(showroomEntity);
    }
}
