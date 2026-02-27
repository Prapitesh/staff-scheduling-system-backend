package com.staffrotationsystem.staffrotation.service;

import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.exception.ResourceNotFoundException;
import com.staffrotationsystem.staffrotation.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

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

    //Get all staff
    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
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
}
