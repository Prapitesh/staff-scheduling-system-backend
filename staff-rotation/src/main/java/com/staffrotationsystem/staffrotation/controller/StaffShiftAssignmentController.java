package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.dto.SpecificStaffMemberDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftCountDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftDTO;
import com.staffrotationsystem.staffrotation.dto.StaffShiftSummaryDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.entity.StaffShiftAssignment;
import com.staffrotationsystem.staffrotation.service.StaffShiftAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/manual")
    public ResponseEntity<StaffShiftAssignment> createStaffShiftAssignment(@RequestParam Long staffId, @RequestParam Long shiftId,@RequestParam LocalDate shiftDate) {
        return ResponseEntity.ok(staffShiftAssignmentService.assignShift(staffId,shiftId,shiftDate));
    }

    @GetMapping
    public ResponseEntity<List<StaffShiftAssignment>> findAllStaffShiftAssignments() {
        return ResponseEntity.ok(staffShiftAssignmentService.findAllStaffShiftAssignments());
    }

    @GetMapping("staff/{staffId}")
    public ResponseEntity<List<StaffShiftAssignment>> findAllStaffShiftAssignmentsByStaffId(@PathVariable Long staffId,@RequestParam LocalDate weekStart) {
        return ResponseEntity.ok(staffShiftAssignmentService.getWeeklyAssignmentsForStaff(staffId,weekStart));
    }

    @PutMapping("/{assignmentId}")
    public StaffShiftAssignment updateshiftdateofanassignment(@PathVariable Long assignmentId,@RequestParam LocalDate shiftDate) {
        return staffShiftAssignmentService.updateshiftdateofanassignment(assignmentId,shiftDate);
    }

    @GetMapping("/date")
    public ResponseEntity<List<StaffShiftAssignment>> findAllStaffShiftAssignmentsByShiftDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate shiftDate) {
        return ResponseEntity.ok(staffShiftAssignmentService.getAllStaffWorkingOnSpecificDate(shiftDate));
    }

    @GetMapping("/count")
    public ResponseEntity<List<StaffShiftCountDTO>> countshiftsperStaff(){
        return ResponseEntity.ok(staffShiftAssignmentService.countshiftsperstaff());
    }

    @GetMapping("/heavy-workers")
    public ResponseEntity<List<StaffShiftCountDTO>> getallstaffmorethanxshifts(@RequestParam Long minShifts){
        return ResponseEntity.ok(staffShiftAssignmentService.getStaffMorethanXShifts(minShifts));
    }

    @GetMapping("/most-loaded")
    public ResponseEntity<List<StaffShiftCountDTO>> getmostloadedshifts(){
        return ResponseEntity.ok(staffShiftAssignmentService.getMostLoadedStaff());
    }

    //TASK 15 — Swap Shifts Between Two Staff
    @PostMapping("/swap")
    public ResponseEntity<String> swapshifts(@RequestParam Long assignmentId1,@RequestParam Long assignmentId2){
        staffShiftAssignmentService.swapshifts(assignmentId1,assignmentId2);
        return  ResponseEntity.ok("Shifts swapped successfully");
    }

    @GetMapping("{staffId}/shifts")
    public ResponseEntity<List<SpecificStaffMemberDTO>> getspecificStaffmembers(@PathVariable Long staffId) {
        return ResponseEntity.ok(staffShiftAssignmentService.getSpecificStaffMembers(staffId));
    }

    //get all shift assignments between two dates.
    @GetMapping("/date-range")
    public ResponseEntity<List<StaffShiftDTO>> getallshiftassignmentsbetwenntwodates(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return ResponseEntity.ok(staffShiftAssignmentService.getallassignmentsbetweentwodates(start,end));
    }


    //get all staff members working on different shifts in month
    @GetMapping("/monthly-shifts")
    public ResponseEntity<List<String>> getStaffWorkingonDifferentShiftsinmonth(@RequestParam int month,@RequestParam int year){
        return ResponseEntity.ok(staffShiftAssignmentService.getStaffWorkingonDifferentShiftsinmonth(month,year));
    }
}
