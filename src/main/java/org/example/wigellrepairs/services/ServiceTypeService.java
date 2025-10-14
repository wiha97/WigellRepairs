package org.example.wigellrepairs.services;

import java.util.List;

import org.example.wigellrepairs.dtos.services.*;
import org.example.wigellrepairs.entities.ServiceType;

public interface ServiceTypeService {

    List<?> getAllServices();
    ShowServiceAdminDTO addService(CreateServiceTypeDTO dto);
    ShowServiceAdminDTO updateService(EditServiceTypeDTO dto);
    void deleteService(Long id);
    ServiceType findExisting(Long id);

}
