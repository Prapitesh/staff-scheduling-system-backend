package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.dto.ConflictDetectionDTO;
import com.staffrotationsystem.staffrotation.service.StaffShiftAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/schedule")
@RequiredArgsConstructor
public class AdminScheduleController {

    private final StaffShiftAssignmentService assignmentService;

    @PostMapping("/generate-next-month")
    public ResponseEntity<String> generateNextMonthSchedule(
            @RequestParam String generatedBy) {

        assignmentService.generateNextMonthSchedule(generatedBy);

        return ResponseEntity.ok("Next month schedule generated successfully");
    }

    @GetMapping("/conflicts")
    public ResponseEntity<ConflictDetectionDTO> detectConflicts() {

        return ResponseEntity.ok(
                assignmentService.detectConflicts()
        );
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportScheduleCSV() {

        String csv = assignmentService.exportScheduleToCSV();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=schedule.csv")
                .body(csv);
    }
}
