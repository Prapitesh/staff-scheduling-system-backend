package com.staffrotationsystem.staffrotation.service;

import com.staffrotationsystem.staffrotation.dto.StaffWithoutShiftDTO;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.enums.AvailabilityStatus;
import com.staffrotationsystem.staffrotation.enums.StaffRole;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.exception.InvalidOperationException;
import com.staffrotationsystem.staffrotation.exception.ResourceNotFoundException;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public Staff updateAvailability(Long staffId, AvailabilityStatus status) {

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setStatus(status);

        return staffRepository.save(staff);
    }

    //Create staff
    public Staff createStaff(Staff staff) {
        if(staffRepository.findByEmail(staff.getEmail()).isPresent()){
            throw new DuplicateResourceException("Email already exists");
        }
       if(staff.getActive()==null){

           staff.setActive(true);
       }
        return staffRepository.save(staff);
    }

    public List<Staff> getAAlActiveStaff(){
        return staffRepository.findByActiveTrue();
    }

    //Get staff by ID
    public Staff getStaffById(Long id){
//        Staff staff=staffRepository.findById(id).get();
//        return staff;
        Staff staff=staffRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Staff not found with id "+id));
        return staff;
    }

    //Delete Staff
    public void deleteStaff(Long id){
        if(!staffRepository.existsById(id)){
            throw new ResourceNotFoundException("Staff not found with id "+id);
        }
        staffRepository.deleteById(id);
    }

    public Staff updateStaff(Long id, Staff updatedStaff){
        Staff existingStaff=staffRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Staff not found with id "+id));
        if(!existingStaff.getEmail().equals(updatedStaff.getEmail())){
            if(staffRepository.findByEmail(updatedStaff.getEmail()).isPresent()){
                throw new DuplicateResourceException("Email already exists");
            }
            existingStaff.setEmail(updatedStaff.getEmail());
        }
        existingStaff.setName(updatedStaff.getName());
        existingStaff.setRole(updatedStaff.getRole());
        existingStaff.setDepartment(updatedStaff.getDepartment());
        if(updatedStaff.getActive()!=null){
            existingStaff.setActive(updatedStaff.getActive());
        }
        return staffRepository.save(existingStaff);
    }

    public void deactivateStaff(Long id) {
        Staff existingStaff = staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff not found with id " + id));
if(!existingStaff.getActive()){
    throw new InvalidOperationException("Staff is already deactivated");
}
        existingStaff.setActive(false);
staffRepository.save(existingStaff);
    }

    public List<Staff> getStaffByRole(StaffRole staffRole){
        return staffRepository.findByRoleAndActiveTrue(staffRole);
    }
//    public List<Staff> getActiveStaff(){
//        return staffRepository.findByActiveTrue();
//    }

    public List<Staff> getStaffByDepartment(String department){
        return staffRepository.findByDepartmentAndActiveTrue(department);
    }

    public Page<Staff> getStaffWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return staffRepository.findByActiveTrue(pageable);
    }

    public Page<Staff> filterstaffbyroleanddepartment(StaffRole staffRole, String department,int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        if(staffRole!=null&&department!=null){
            return staffRepository.findByRoleAndDepartmentIgnoreCaseAndActiveTrue(staffRole,department,pageable);
        }
        else if (staffRole != null) {
            return staffRepository.findByRoleAndActiveTrue(staffRole, pageable);
        }
        else if(department!=null)
        {
            return staffRepository.findByDepartmentIgnoreCaseAndActiveTrue(department, pageable);
        }
        return staffRepository.findByActiveTrue(pageable);
    }

    public List<StaffWithoutShiftDTO> getstaffWithoutShifts(){
        return staffRepository.findStaffWithoutShifts();
    }

}
