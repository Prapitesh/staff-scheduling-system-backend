package com.staffrotationsystem.staffrotation.service;

import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.exception.DuplicateResourceException;
import com.staffrotationsystem.staffrotation.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;

    public Shift createShift(Shift shift){
        if(shiftRepository.findByShiftType(shift.getShiftType()).isPresent()){
            throw new DuplicateResourceException("Shift type already exists");
        }
        return shiftRepository.save(shift);
    }

    public List<Shift> getAllShifts(){
        return shiftRepository.findAll();
    }

    public Shift getShiftById(Long shiftId){
        return shiftRepository.findById(shiftId).orElseThrow(()->new RuntimeException("Shift not found"));
    }


}

