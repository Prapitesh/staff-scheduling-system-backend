package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.service.StaffShiftAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/rotation")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminRotationController {

    private final StaffShiftAssignmentService staffShiftAssignmentService;

    @PostMapping("/generate-next-month")
    public ResponseEntity<String> generateNextMonthRotation() {

        staffShiftAssignmentService.generateNextMonthRotation();

        return ResponseEntity.ok("Next month rotation generated successfully");
    }

    @PostMapping("/regenerate")
    public ResponseEntity<String> regenerateRotation(
            @RequestParam LocalDate startDate,
            @RequestParam int days) {

        staffShiftAssignmentService.regenerateRotation(startDate, days);

        return ResponseEntity.ok("Rotation regenerated successfully");
    }


    @PutMapping("/override")
    public ResponseEntity<StaffShiftAssignment> overrideShift(
            @RequestParam Long assignmentId,
            @RequestParam Long newStaffId) {

        return ResponseEntity.ok(
                staffShiftAssignmentService.reassignShift(assignmentId, newStaffId)
        );
    }
}
