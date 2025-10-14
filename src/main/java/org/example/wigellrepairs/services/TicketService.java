package org.example.wigellrepairs.services;

import java.util.List;

import org.example.wigellrepairs.dtos.tickets.*;
import org.example.wigellrepairs.entities.RepairTicket;

public interface TicketService {

    ShowTicketDTO createTicket(BookTicketDTO repairTicket);
    List<ShowTicketDTO> listMyTickets();
    List<ShowTicketDTO> listCancelled();
    List<ShowTicketDTO> listUpcoming();
    List<ShowTicketDTO> listPast();
    ShowTicketDTO cancelTicket(CancelTicketDTO repairTicket);
    RepairTicket findExisting(Long id);
}
