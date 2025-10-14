package org.example.wigellrepairs.services;

import java.util.ArrayList;
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

    private final int MAX_NAME_LENGTH = 25;

    @Autowired
    public TechnicianServiceImpl(TechnicianRepo techRepo, DTOMapperService mapperService){
        this.techRepo = techRepo;
        this.mapperService = mapperService;
    }

	@Override
	public ShowTechnicianDTO addTechnician(CreateTechnicianDTO dto) {
        Checker.valueCheck(dto.getName(), "name");
        String name = dto.getName();
        if(techRepo.existsByNameIgnoreCase(name))
            throw new ResourceExistsException("Technician", "name", name);

        Checker.lengthCheck(dto.getName(), MAX_NAME_LENGTH, "Name");
        Checker.valueCheck(dto.getExpertise(), "expertise");

        Technician technician = new Technician(
            dto.getName(),
            dto.getExpertise()
        );

        techRepo.save(technician);

        return mapperService.toTechnicianDTO(technician);
	}

	@Override
	public List<ShowTechnicianDTO> getTechnicians() {
        List<ShowTechnicianDTO> showListDtos = new ArrayList<>();
        for(Technician tech : techRepo.findAll()){
            showListDtos.add(new ShowTechnicianDTO(
                tech.getId(),
                tech.getName(),
                tech.getExpertise()));
        }
        return showListDtos;
	}

	@Override
	public Technician findExisting(Long id) {
        return techRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Technician", id));
	}
}
