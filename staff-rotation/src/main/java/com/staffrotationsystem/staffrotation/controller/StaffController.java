package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    //Create Staff
    @PostMapping
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff staff) {
        Staff savedStaff = staffService.createStaff(staff);
        return ResponseEntity.ok(savedStaff);
    }

    //Get All Staff
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    //Get staff By ID
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id){
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    //Delete Staff
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id){
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted sucessfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @Valid @RequestBody Staff updatedStaff){
        return ResponseEntity.ok(staffService.updateStaff(id, updatedStaff));
    }
}
