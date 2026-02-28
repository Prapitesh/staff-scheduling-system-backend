package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.service.StaffShiftAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class StaffShiftAssignmentController {


    private final StaffShiftAssignmentService staffShiftAssignmentService;

    @PostMapping
    public ResponseEntity<String> assignShift(@RequestParam Long shiftId, @RequestParam Long staffId, @RequestParam LocalDate shiftDate) {
        staffShiftAssignmentService.assignShift(shiftId, staffId, shiftDate);
        return ResponseEntity.ok("Assignment created successfully");
    }

    @GetMapping
    public ResponseEntity<List<StaffShiftAssignment>> findAllStaffShiftAssignments() {
        return ResponseEntity.ok(staffShiftAssignmentService.findAllStaffShiftAssignments());
    }
}
