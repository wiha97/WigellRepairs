package org.example.wigellrepairs.services;

import java.util.ArrayList;
import java.util.List;

import org.example.wigellrepairs.dtos.services.*;
import org.example.wigellrepairs.dtos.technicians.MinimalTechnicianDTO;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.entities.Technician;
import org.example.wigellrepairs.exceptions.ResourceExistsException;
import org.example.wigellrepairs.exceptions.ResourceNotFoundException;
import org.example.wigellrepairs.repositories.ServiceTypeRepo;
import org.example.wigellrepairs.utils.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepo serviceTypeRepo;
    private final TechnicianService techService;
    private final DTOMapperService mapperService;
    private final AuthService authService;
    private final LoggerService loggerService;

    private final int MAX_NAME_LENGTH = 35;

    @Autowired
    public ServiceTypeServiceImpl(ServiceTypeRepo serviceTypeRepo, DTOMapperService mapperService, AuthService authService, TechnicianService techService, LoggerService loggerService) {
        this.serviceTypeRepo = serviceTypeRepo;
        this.mapperService = mapperService;
        this.authService = authService;
        this.techService = techService;
        this.loggerService = loggerService;
    }

	@Override
	public List<?> getAllServices() {
        if(authService.getRole().equals("ADMIN"))
            return getAllServicesAdmin();
        return getAllServicesCustomer();
	}

    private List<ShowServiceTypeDTO> getAllServicesCustomer(){
        return mapperService.toServiceListDTO(serviceTypeRepo.findAll());
    }

	private List<ShowServiceAdminDTO> getAllServicesAdmin() {
        return mapperService.toServiceAdminListDTO(serviceTypeRepo.findAll());
	}

    @Transactional
	@Override
	public ShowServiceAdminDTO addService(CreateServiceTypeDTO dto) {
        Checker.valueCheck(dto.getName(), "name");
        String name = dto.getName();
        if(serviceTypeRepo.existsByNameIgnoreCase(name)) {
            loggerService.error("Admin tried to add service that already exists: " + name);
            throw new ResourceExistsException("Service", "name", name);
        }

        Checker.lengthCheck(dto.getName(), MAX_NAME_LENGTH, "Name");
        Checker.valueCheck(dto.getSek(), "sek");
        Checker.valueCheck(dto.getCategory(), "category");

        ServiceType service = new ServiceType();
        service.setName(dto.getName());
        service.setSek(dto.getSek());
        service.setCategory(dto.getCategory());
        service.setTechnicians(getTechnicians(dto.getTechnician()));

        loggerService.info("Added new service: " + name);

        return mapperService.toServiceAdminDTO(serviceTypeRepo.save(service));
	}

    @Transactional
	@Override
	public ShowServiceAdminDTO updateService(EditServiceTypeDTO dto) {
        ServiceType service = findExisting(dto.getId());

        Checker.valueCheck(dto.getName(), "name");
        Checker.lengthCheck(dto.getName(), MAX_NAME_LENGTH, "Name");

        Checker.valueCheck(dto.getSek(), "sek");
        Checker.valueCheck(dto.getCategory(), "category");

        service.setName(dto.getName());
        service.setSek(dto.getSek());
        service.setCategory(dto.getCategory());
        service.setTechnicians(getTechnicians(dto.getTechnician()));

        loggerService.info("Updated service with id " + dto.getId());

        return mapperService.toServiceAdminDTO(serviceTypeRepo.save(service));
	}

    private List<Technician> getTechnicians(List<MinimalTechnicianDTO> technicianDTOs){
        List<Technician> technicians = new ArrayList<>();
        for(MinimalTechnicianDTO tech : technicianDTOs){
            technicians.add(techService.findExisting(tech.getId()));
        }
        return technicians;
    }

	@Override
	public void deleteService(Long id) {
        if(serviceTypeRepo.existsById(id)) {
            serviceTypeRepo.deleteById(id);
            loggerService.info("Deleted service with id " + id);
        } else {
            loggerService.error("Tried to delete nonexistent service with id " + id);
            throw new ResourceNotFoundException("Service", id);
        }
	}

    @Override
    public ServiceType findExisting(Long id){
        return serviceTypeRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service", id));
    }

}
