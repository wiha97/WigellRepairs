package org.example.wigellrepairs.controllers;

import java.util.List;

import org.example.wigellrepairs.dtos.technicians.CreateTechnicianDTO;
import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.services.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechsController extends BaseController {

    @Autowired
    private TechnicianService techService;

    @PostMapping("/addtechnician")
    public ResponseEntity<ShowTechnicianDTO> addTechnician(@RequestBody CreateTechnicianDTO technician) {
        return ResponseEntity.ok(techService.addTechnician(technician));
    }

    @GetMapping("/technicians")
    public ResponseEntity<List<ShowTechnicianDTO>> getTechnicians() {
        return ResponseEntity.ok(techService.getTechnicians());
    }
}
