package org.example.wigellrepairs.dtos.tickets;

import org.example.wigellrepairs.dtos.services.ShowServiceTypeDTO;

public class ShowTicketDTO {
    private Long id;
    private String customer;
    private Long date;
    private boolean canceled;
    private double totalSek;
    private double totalEur;
    private ShowServiceTypeDTO service;

    public ShowTicketDTO(Long id, String customer, Long date, boolean canceled, double totalSek, double totalEur, ShowServiceTypeDTO service) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.canceled = canceled;
        this.totalSek = totalSek;
        this.totalEur = totalEur;
        this.service = service;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public double getTotalSek() {
		return totalSek;
	}

	public void setTotalSek(double totalSek) {
		this.totalSek = totalSek;
	}

	public double getTotalEur() {
		return totalEur;
	}

	public void setTotalEur(double totalEur) {
		this.totalEur = totalEur;
	}

	public ShowServiceTypeDTO getService() {
		return service;
	}

	public void setService(ShowServiceTypeDTO service) {
		this.service = service;
	}
}
