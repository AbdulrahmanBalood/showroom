package car.showroom.project.entitiy;

import car.showroom.project.util.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE showroom SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "showroom")
public class ShowroomEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100)
    @NotEmpty
    private String name;
    @Column(unique = true,name = "commercial_registration_number")
    @NotEmpty(message = "Commercial Registration Number is required")
    @Size(max = 15)
    @Pattern(regexp = "\\d{1,15}", message = "Commercial Registration Number must be numeric and up to 15 digits")
    private String commercialRegistrationNumber;
    @Column(name = "manager_name")
    @Size(max = 100, message = "Manager name cannot exceed 100 characters")
    private String managerName;
    @Column(name = "phone_number")
    @NotEmpty
    @Size(max = 15)
    @Pattern(regexp = "\\d{1,15}", message = "Phone number must be numeric and up to 15 digits")
    private String phoneNumber;
    @Size(max = 255)
    private String address;
    @JsonManagedReference
    @OneToMany(mappedBy = "showroomEntity")
    private List<CarEntity> carEntities;
}
