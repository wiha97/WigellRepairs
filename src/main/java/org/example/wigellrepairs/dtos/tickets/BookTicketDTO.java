package org.example.wigellrepairs.dtos.tickets;

import org.example.wigellrepairs.dtos.services.MinimalServiceDTO;

public class BookTicketDTO {
    private Long date;
    private MinimalServiceDTO service;

    public BookTicketDTO() {
    }

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public MinimalServiceDTO getService() {
		return service;
	}

	public void setService(MinimalServiceDTO service) {
		this.service = service;
	}
}
