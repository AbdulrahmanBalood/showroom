package car.showroom.project.entitiy;

import car.showroom.project.util.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE car SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "car")
public class CarEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "VIN is required")
    @Size(max = 25, message = "VIN cannot exceed 25 characters")
    private String vin;

    @NotEmpty(message = "Maker is required")
    @Size(max = 25, message = "Maker cannot exceed 25 characters")
    private String maker;

    @NotEmpty(message = "Model is required")
    @Size(max = 25, message = "Model cannot exceed 25 characters")
    private String model;

    @NotEmpty(message = "Model year is required")
    @Pattern(regexp = "\\d{4}", message = "Model year must be a 4-digit number")
    @Column(name = "model_year", length = 4)
    private String modelYear;

    @NotNull(message = "Price is required")
    private Double price;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private ShowroomEntity showroomEntity;
}
