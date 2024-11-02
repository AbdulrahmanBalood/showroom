package car.showroom.project.entitiy;

import car.showroom.project.util.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE showroom SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "showroom")
public class Showroom extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100)
    @NotEmpty
    private String name;
    @Column(unique = true,name = "commercial_registration_number")
    @NotEmpty(message = "Commercial Registration Number is required")
    private String commercialRegistrationNumber;
    @Column(name = "manager_name")
    @Size(max = 100)
    private String managerName;
    @Column(name = "phone_number")
    @Max(value = 999999999999999L, message = "Phone number cannot exceed 15 digits")
    @NonNull
    private Integer phoneNumber;
    @Size(max = 255)
    private String address;
    @JsonManagedReference
    @OneToMany(mappedBy = "showroom")
    private List<Car> cars;
}
