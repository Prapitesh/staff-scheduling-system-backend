package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.entity.Shift;
import com.staffrotationsystem.staffrotation.entity.Staff;
import com.staffrotationsystem.staffrotation.service.RotationService;
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
@RequestMapping("/api/rotation")
@RequiredArgsConstructor
public class RotationController {

    private final RotationService rotationService;

    @PostMapping
    public ResponseEntity<String> generateWeeklyRotation(@RequestParam LocalDate weekStart){
        rotationService.generateWeeklyRotation(weekStart);
        return ResponseEntity.ok("Weekly rotation generated");
    }

//    @GetMapping
//    public ResponseEntity<List<Shift>> findAllRotations(){
//        return ResponseEntity.ok(rotationService.getAllRotation());
//    }
}
