package org.example.wigellrepairs.controllers;

import java.util.List;

import org.example.wigellrepairs.dtos.tickets.*;
import org.example.wigellrepairs.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketsController extends BaseController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookservice")
    public ResponseEntity<ShowTicketDTO> bookService(@RequestBody BookTicketDTO repairTicket){
        return ResponseEntity.ok(ticketService.createTicket(repairTicket));
    }

    @PutMapping("/cancelbooking")
    public ResponseEntity<ShowTicketDTO> cancelTicket(@RequestBody CancelTicketDTO repairTicket){
        return ResponseEntity.ok(ticketService.cancelTicket(repairTicket));
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<ShowTicketDTO>> myTickets(){
        return ResponseEntity.ok(ticketService.listMyTickets());
    }

    @GetMapping("/listcanceled")
    public ResponseEntity<List<ShowTicketDTO>> getCancelled(){
        return ResponseEntity.ok(ticketService.listCancelled());
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<ShowTicketDTO>> getUpcoming(){
        return ResponseEntity.ok(ticketService.listUpcoming());
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<ShowTicketDTO>> getPast(){
        return ResponseEntity.ok(ticketService.listPast());
    }

}
