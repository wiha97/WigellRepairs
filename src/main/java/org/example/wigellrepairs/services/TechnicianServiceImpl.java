package org.example.wigellrepairs.services;

import java.util.List;

import org.example.wigellrepairs.dtos.technicians.CreateTechnicianDTO;
import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.entities.Technician;
import org.example.wigellrepairs.exceptions.ResourceExistsException;
import org.example.wigellrepairs.exceptions.ResourceNotFoundException;
import org.example.wigellrepairs.repositories.TechnicianRepo;
import org.example.wigellrepairs.utils.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    private final TechnicianRepo techRepo;
    private final DTOMapperService mapperService;
    private final LoggerService loggerService;

    private final int MAX_NAME_LENGTH = 25;

    @Autowired
    public TechnicianServiceImpl(TechnicianRepo techRepo, DTOMapperService mapperService, LoggerService loggerService){
        this.techRepo = techRepo;
        this.mapperService = mapperService;
        this.loggerService = loggerService;
    }

	@Override
	public ShowTechnicianDTO addTechnician(CreateTechnicianDTO dto) {
        Checker.valueCheck(dto.getName(), "name");
        String name = dto.getName();
        if(techRepo.existsByNameIgnoreCase(name)) {
            loggerService.error("Failed to add technician with existing name: " + name);
            throw new ResourceExistsException("Technician", "name", name);
        }

        Checker.lengthCheck(dto.getName(), MAX_NAME_LENGTH, "Name");
        Checker.valueCheck(dto.getExpertise(), "expertise");

        Technician technician = new Technician(
            dto.getName(),
            dto.getExpertise()
        );

        techRepo.save(technician);

        loggerService.info("Added new technician: " + technician.getName());

        return mapperService.toTechnicianDTO(technician);
	}

	@Override
	public List<ShowTechnicianDTO> getTechnicians() {
        return mapperService.toTechnicianListDTO(techRepo.findAll());
	}

	@Override
	public Technician findExisting(Long id) {
        return techRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Technician", id));
	}
}
