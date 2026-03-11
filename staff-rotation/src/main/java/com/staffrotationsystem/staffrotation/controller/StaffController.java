package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.dto.StaffWithoutShiftDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import com.staffrotationsystem.staffrotation.enums.StaffRole;
import com.staffrotationsystem.staffrotation.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
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

    //get all active staff
    @GetMapping("/active")
    public ResponseEntity<List<Staff>> getAllActiveStaff(){
        return ResponseEntity.ok(staffService.getAAlActiveStaff());
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

    @PutMapping("/{id}/availability")
    public ResponseEntity<Staff> updateAvailability(
            @PathVariable Long id,
            @RequestParam AvailabilityStatus status) {

        return ResponseEntity.ok(staffService.updateAvailability(id, status));
    }

    @PutMapping("{id}/deactivate")
    public ResponseEntity<String> deactivateStaff(@PathVariable Long id){
        staffService.deactivateStaff(id);
        return ResponseEntity.ok("Staff deactivated successfully");
    }

   @GetMapping("/by-role")
   public ResponseEntity<List<Staff>> getStaffByRole(StaffRole staffRole){
        return ResponseEntity.ok(staffService.getStaffByRole(staffRole));
   }

    //Get staff By ID
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id){
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @GetMapping("/department")
    public ResponseEntity<List<Staff>> getStaffByDepartment(String department){
        return ResponseEntity.ok(staffService.getStaffByDepartment(department));
    }

    @GetMapping("/pagination")
public ResponseEntity<Page<Staff>> getStaffWithPagination(@RequestParam int  page, @RequestParam int size){
        return ResponseEntity.ok(staffService.getStaffWithPagination(page, size));
}

     @GetMapping("search")
     public ResponseEntity<Page<Staff>> filterstaffbyroleanddepartment(@RequestParam(required = false) StaffRole staffRole,@RequestParam(required = false) String department,@RequestParam int page,@RequestParam int size){
        return ResponseEntity.ok(staffService.filterstaffbyroleanddepartment(staffRole,department,page,size));
     }

     @GetMapping("/no-shifts")
     public ResponseEntity<List<StaffWithoutShiftDTO>> getstaffWithoutShifts(){
        return ResponseEntity.ok(staffService.getstaffWithoutShifts());
     }
}
