package com.staffrotationsystem.staffrotation.controller;

import com.staffrotationsystem.staffrotation.dto.StaffAvailabilityRequestDTO;
import com.staffrotationsystem.staffrotation.entity.StaffAvailability;
import com.staffrotationsystem.staffrotation.service.StaffAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class StaffAvailabilityController {

    private final StaffAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<StaffAvailability> markAvailability(
            @RequestBody StaffAvailabilityRequestDTO request) {

        return ResponseEntity.ok(availabilityService.markAvailability(request));
    }
}