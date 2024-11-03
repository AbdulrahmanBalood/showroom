package car.showroom.project.dto.Car;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarPageDto {
    private String uuid;

    private String vin;

    private String maker;

    private String model;

    private String modelYear;

    private Double price;

    private String showroomName;

    private String showroomPhoneNumber;
}
