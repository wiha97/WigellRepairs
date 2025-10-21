package org.example.wigellrepairs.services;

import java.util.ArrayList;
import java.util.List;

import org.example.wigellrepairs.dtos.tickets.*;
import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.exceptions.InPastException;
import org.example.wigellrepairs.exceptions.DidNotCancelInTimeException;
import org.example.wigellrepairs.exceptions.ResourceNotFoundException;
import org.example.wigellrepairs.exceptions.ServiceTimeUnavailableException;
import org.example.wigellrepairs.exceptions.UnauthorizedException;
import org.example.wigellrepairs.repositories.TicketRepo;
import org.example.wigellrepairs.utils.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private final ServiceTypeService typeService;
    private final AuthService authService;
    private final TimeService timeService;
    private final LoggerService loggerService;
    private final DTOMapperService mapperService;

    @Autowired
    public TicketServiceImpl(TicketRepo ticketRepo, AuthService authService, LoggerService loggerService, DTOMapperService mapperService, ServiceTypeService typeService, TimeService timeService){
        this.ticketRepo = ticketRepo;
        this.authService = authService;
        this.loggerService = loggerService;
        this.mapperService = mapperService;
        this.typeService = typeService;
        this.timeService = timeService;
    }

	@Override
	public ShowTicketDTO createTicket(BookTicketDTO dto) {
        long workTime = 2700; // 2700 = 45min
        long curTime = timeService.getCurrentUTC();
        String dateFormat = "yyyy-MM-dd HH:mm";

        Checker.valueCheck(dto.getDate(), "date");
        if(dto.getDate() < curTime) {
            String time = timeService.formatted(dto.getDate(), dateFormat);
            loggerService.error("User tried to book a new ticket in the past: " + time);
            throw new InPastException("book a ticket", time);
        }

        Checker.valueCheck(dto.getService(), "service");
        ServiceType service = typeService.findExisting(dto.getService().getId());

        long bookDate = dto.getDate();
        String category = service.getCategory().name().toLowerCase();
        String formattedBookDate = timeService.formatted(bookDate, dateFormat);

        List<RepairTicket> relatedTickets = ticketRepo.findByServiceCategory(service.getCategory());
        // Kollar om det redan finns en bokning för den kategorin/teknikern vid den tiden
        if(relatedTickets.stream().anyMatch(
            (t)->t.getDate() + workTime > bookDate &&
            t.getDate() < bookDate + workTime)){

            loggerService.error(String.format(
                "User tried to book %s service at an unavailable time: %s",
                category, formattedBookDate));

            throw new ServiceTimeUnavailableException(
                category, formattedBookDate, timeService.formatted(bookDate + workTime, "HH:mm")
            );
        }

        RepairTicket ticket = new RepairTicket(
            authService.getUserName(),
            bookDate,
            service.getSek(),
            service
        );

        ticketRepo.save(ticket);

        loggerService.info(String.format("User booked a new %s service for %s", category, formattedBookDate));

        return mapperService.toTicketDTO(ticket);
	}

	@Override
	public List<ShowTicketDTO> listMyTickets() {
        List<ShowTicketDTO> dtos = new ArrayList<>();
        for(RepairTicket ticket : ticketRepo.findByCustomerContaining(authService.getUserName())){
            dtos.add(mapperService.toTicketDTO(ticket));
        }
        return dtos;
	}

	@Override
	public ShowTicketDTO cancelTicket(CancelTicketDTO dto) {
        RepairTicket ticket = findExisting(dto.getId());

        if(!authService.getUserName().equals(ticket.getCustomer())) {
            loggerService.error("User tried to cancel a ticket that was not theirs");
            throw new UnauthorizedException("ticket");
        }

        long tTime = ticket.getDate();
        long cDate = timeService.getCurrentUTC();
        long day = 86400; // 24tim
        if(tTime + day < cDate){
            String time = timeService.formatted(tTime, "yyyy-MM-dd HH:mm");
            loggerService.error("User did not cancel in time: " + time);
            throw new DidNotCancelInTimeException(time);
        }

        ticket.setCanceled(true);
        loggerService.info("User canceled " + ticket.getService().getName());
        return mapperService.toTicketDTO(ticketRepo.save(ticket));
	}

	@Override
	public List<ShowTicketDTO> listCancelled() {
        return mapperService.toTicketListDTO(
            ticketRepo.findAll().stream()
                .filter((t)-> t.isCanceled()).toList()
            );
}

	@Override
	public List<ShowTicketDTO> listUpcoming() {
        return mapperService.toTicketListDTO(
            ticketRepo.findAll().stream()
                .filter((t)-> t.getDate() > timeService.getCurrentUTC()).toList()
            );
	}

	@Override
	public List<ShowTicketDTO> listPast() {
        return mapperService.toTicketListDTO(
            ticketRepo.findAll().stream()
                .filter((t)-> t.getDate() < timeService.getCurrentUTC()).toList()
            );
	}

    @Override
    public RepairTicket findExisting(Long id){
        return ticketRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service", id));
    }
}
