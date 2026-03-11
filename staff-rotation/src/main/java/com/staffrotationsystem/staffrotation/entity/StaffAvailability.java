package com.staffrotationsystem.staffrotation.entity;

import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "staff_availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;


    public StaffAvailability(LocalDate date, AvailabilityStatus status, Staff staff) {
        this.date = date;
        this.status = status;
        this.staff = staff;
    }
}