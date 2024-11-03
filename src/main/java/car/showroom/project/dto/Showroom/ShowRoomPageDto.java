package car.showroom.project.dto.Showroom;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowRoomPageDto {
    private String name;
    private String commercialRegistrationNumber;
    private String phoneNumber;
}
