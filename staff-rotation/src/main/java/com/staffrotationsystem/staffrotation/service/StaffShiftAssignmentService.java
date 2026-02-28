package com.staffrotationsystem.staffrotation.service;


import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.repository.ShiftRepository;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import com.staffrotationsystem.staffrotation.repository.StaffShiftAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffShiftAssignmentService {

    private final StaffShiftAssignmentRepository assignmentRepository;

    private final StaffRepository staffRepository;

    private final ShiftRepository shiftRepository;

    public StaffShiftAssignment assignShift(Long shiftId, Long staffId, LocalDate shiftDate) {
        Staff staff=staffRepository.findById(staffId).orElseThrow(()->new RuntimeException("Staff not found"));
        Shift shift=shiftRepository.findById(shiftId).orElseThrow(()->new RuntimeException("Shift not found"));
        if(assignmentRepository.findByStaffAndShiftDate(staff,shiftDate).isPresent()){
            throw new DuplicateResourceException("Staff already assigned a shift on this shift");
        }
        StaffShiftAssignment assignment=StaffShiftAssignment.builder()
                        .staff(staff)
                        .shift(shift)
                        .shiftDate(shiftDate)
                        .build();
        return assignmentRepository.save(assignment);
    }


    public List<StaffShiftAssignment> findAllStaffShiftAssignments() {
        return assignmentRepository.findAll();
    }
}
