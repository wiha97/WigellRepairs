package org.example.wigellrepairs.services;

import java.util.List;

import org.example.wigellrepairs.dtos.technicians.CreateTechnicianDTO;
import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.entities.Technician;

public interface TechnicianService {

    ShowTechnicianDTO addTechnician(CreateTechnicianDTO technician);
    List<ShowTechnicianDTO> getTechnicians();
    Technician findExisting(Long id);
}
