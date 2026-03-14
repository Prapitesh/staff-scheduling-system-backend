package com.staffrotationsystem.staffrotation.entity;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import com.staffrotationsystem.staffrotation.enums.StaffRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Entity
@Table(name="staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    @Column(nullable=false)
    private String name;
    @Email(message = "Invalid email format")
    @Column(nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffRole role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private Boolean active=true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus status = AvailabilityStatus.AVAILABLE;

    @ElementCollection
    @CollectionTable(name = "staff_skills", joinColumns = @JoinColumn(name = "staff_id"))
    @Column(name = "skill")
    private java.util.Set<String> skills;

}
