package car.showroom.project.dto;

import car.showroom.project.entitiy.Car;
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
public class ShowroomDto {
    private String uuid = UUID.randomUUID().toString().toLowerCase();
    @Size(max = 100)
    @NotEmpty
    private String name;
    @NotEmpty
    private String commercialRegistrationNumber;
    @Size(max = 100)
    private String managerName;
    @Max(value = 999999999999999L, message = "Phone number cannot exceed 15 digits")
    @NonNull
    private Integer phoneNumber;
    @Size(max = 255)
    private String address;
    private List<CarDto> cars;
}
