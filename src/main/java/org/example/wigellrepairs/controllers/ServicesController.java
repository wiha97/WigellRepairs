package org.example.wigellrepairs.controllers;

import java.util.List;

import org.example.wigellrepairs.dtos.services.*;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.services.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicesController extends BaseController {

    @Autowired
    private ServiceTypeService typeService;

    @GetMapping("/services")
    public ResponseEntity<List<?>> getServices(){
        return ResponseEntity.ok(typeService.getAllServices());
    }

    @PostMapping("/addservice")
    public ResponseEntity<ShowServiceAdminDTO> addService(@RequestBody CreateServiceTypeDTO serviceTypeDTO) {
        return ResponseEntity.ok(typeService.addService(serviceTypeDTO));
    }

    @PutMapping("/updateservice")
    public ResponseEntity<ShowServiceAdminDTO> updateService(@RequestBody EditServiceTypeDTO serviceType) {
        return ResponseEntity.ok(typeService.updateService(serviceType));
    }

    @DeleteMapping("/remservice/{id}")
    public ResponseEntity<String> removeService(@PathVariable Long id) {
        typeService.deleteService(id);
        return ResponseEntity.ok(String.format("Service with ID %s successfully removed", id));
    }
}
