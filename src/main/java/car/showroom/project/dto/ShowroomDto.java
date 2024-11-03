package car.showroom.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowroomDto {
    private String uuid;
    @Size(max = 100)
    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = "\\d{1,15}", message = "Commercial Registration Number must be numeric and up to 15 digits")
    private String commercialRegistrationNumber;
    @Size(max = 100, message = "Manager name cannot exceed 100 characters")
    private String managerName;
    @NotEmpty
    @Pattern(regexp = "\\d{1,15}", message = "Phone number must be numeric and up to 15 digits")
    private String phoneNumber;
    @Size(max = 255)
    private String address;
    private List<CarDto> cars;
}
