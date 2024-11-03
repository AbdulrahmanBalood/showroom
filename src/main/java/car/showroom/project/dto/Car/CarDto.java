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
public class CarDto {
    private String uuid;
    @NotEmpty
    @Size(max = 25)
    private String vin;
    @NotEmpty
    @Size(max = 25)
    private String maker;
    @NotEmpty
    @Size(max = 25)
    private String model;
    @NotEmpty
    @Size(max = 25)
    private String modelYear;
    @NotEmpty
    private Double price;

}
