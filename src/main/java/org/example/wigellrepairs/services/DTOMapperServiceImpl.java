package org.example.wigellrepairs.services;

import java.util.ArrayList;
import java.util.List;

import org.example.wigellrepairs.dtos.services.ShowServiceAdminDTO;
import org.example.wigellrepairs.dtos.services.ShowServiceTypeDTO;
import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.dtos.tickets.ShowTicketDTO;
import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.entities.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOMapperServiceImpl implements DTOMapperService {

    private final CurrencyService currencyService;

    @Autowired
    public DTOMapperServiceImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

	@Override
	public ShowServiceTypeDTO toServiceDTO(ServiceType service) {
        double sek = service.getSek();
        return new ShowServiceTypeDTO(
            service.getId(),
            service.getName(),
            sek,
            currencyService.getConversion(sek, "SEK", "EUR"),
            service.getCategory()
        );
	}

	@Override
	public ShowServiceAdminDTO toServiceAdminDTO(ServiceType service) {
        double sek = service.getSek();
        return new ShowServiceAdminDTO(
            service.getId(),
            service.getName(),
            sek,
            currencyService.getConversion(sek, "SEK", "EUR"),
            service.getCategory(),
            toTechnicianListDTO(service.getTechnicians())
        );
	}

	@Override
	public ShowTechnicianDTO toTechnicianDTO(Technician technician) {
        return new ShowTechnicianDTO(
            technician.getId(),
            technician.getName(),
            technician.getExpertise()
        );
	}

	@Override
	public ShowTicketDTO toTicketDTO(RepairTicket ticket) {
        ShowServiceTypeDTO service = toServiceDTO(ticket.getService());
        double sek = ticket.getTotalSek();
        return new  ShowTicketDTO(
            ticket.getId(),
            ticket.getCustomer(),
            ticket.getDate(),
            ticket.isCanceled(),
            sek,
            currencyService.getConversion(sek, "SEK", "EUR"),
            service
        );
	}

	@Override
	public List<ShowTicketDTO> toTicketListDTO(List<RepairTicket> tickets) {
        List<ShowTicketDTO> dtos = new ArrayList<>();
        for(RepairTicket ticket : tickets) {
            dtos.add(toTicketDTO(ticket));
        }
        return dtos;
	}

	@Override
	public List<ShowServiceTypeDTO> toServiceListDTO(List<ServiceType> services) {
        List<ShowServiceTypeDTO> dtos = new ArrayList<>();
        for(ServiceType service : services){
            dtos.add(toServiceDTO(service));
        }
        return dtos;
	}

	@Override
	public List<ShowServiceAdminDTO> toServiceAdminListDTO(List<ServiceType> services) {
        List<ShowServiceAdminDTO> dtos = new ArrayList<>();
        for(ServiceType service : services){
            dtos.add(toServiceAdminDTO(service));
        }
        return dtos;
	}

	@Override
	public List<ShowTechnicianDTO> toTechnicianListDTO(List<Technician> technicians) {
        List<ShowTechnicianDTO> dtos = new ArrayList<>();
        for(Technician technician : technicians){
            dtos.add(toTechnicianDTO(technician));
        }
        return dtos;
	}

}
