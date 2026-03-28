package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @PostMapping
    public Shift createShift(@RequestBody Shift shift){
        return shiftService.createShift(shift);
    }

    @GetMapping
    public List<Shift> getAllShifts(){
        return shiftService.getAllShifts();
    }

    @GetMapping("/{id}")
    public Shift getShiftById(@PathVariable Long id){
        return shiftService.getShiftById(id);
    }
}
