package car.showroom.project.entitiy;

import car.showroom.project.util.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE car SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "car")
public class Car extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(name = "model_year")
    private String modelYear;
    @NotEmpty
    private Double price;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;
}
