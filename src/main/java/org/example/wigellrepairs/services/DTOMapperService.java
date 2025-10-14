package org.example.wigellrepairs.services;

import java.util.List;

import org.example.wigellrepairs.dtos.services.ShowServiceAdminDTO;
import org.example.wigellrepairs.dtos.services.ShowServiceTypeDTO;
import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.dtos.tickets.ShowTicketDTO;
import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.entities.Technician;

public interface DTOMapperService {

    ShowServiceTypeDTO toServiceDTO(ServiceType service);
    ShowServiceAdminDTO toServiceAdminDTO(ServiceType service);
    ShowTechnicianDTO toTechnicianDTO(Technician technician);
    ShowTicketDTO toTicketDTO(RepairTicket ticket);

    List<ShowTicketDTO> toTicketListDTO(List<RepairTicket> tickets);
    List<ShowServiceTypeDTO> toServiceListDTO(List<ServiceType> services);
    List<ShowServiceAdminDTO> toServiceAdminListDTO(List<ServiceType> services);
    List<ShowTechnicianDTO> toTechnicianListDTO(List<Technician> technicians);
}
