package car.showroom.project.util;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseEntity {
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "uuid")
    private String uuid;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = SessionUtils.getCurrentUser();
        this.updatedBy = SessionUtils.getCurrentUser();
        if(this.uuid == null) {
            this.uuid = UUID.randomUUID().toString().toLowerCase();
        }
    }
    @PreRemove
    public void preRemove() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
