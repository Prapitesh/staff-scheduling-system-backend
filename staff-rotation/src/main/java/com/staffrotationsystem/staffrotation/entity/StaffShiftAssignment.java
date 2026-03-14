package com.staffrotationsystem.staffrotation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="staff_shift_assignments",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"staff_id","shift_date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffShiftAssignment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id")
    private Staff staff;


    @ManyToOne(optional = false)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "shift_date",nullable = false)
    private LocalDate shiftDate;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private RotationSchedule schedule;
}

