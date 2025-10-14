package org.example.wigellrepairs.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repair_tickets")
public class RepairTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "customer", length = 35, nullable = false)
    private String customer;

    @Column(name = "is_canceled")
    private boolean canceled;

    @Column(name = "date", length = 20, nullable = false)
    private Long date;

    @Column(name = "total_sek", length = 10)
    private Double totalSek;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties("tickets")
    private ServiceType service;

    // @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    // @JoinColumn(name = "technician_id")
    // @JsonIgnoreProperties({"repairTickets", "serviceType"})
    // private Technician technician;

	public RepairTicket() {

    }

    public RepairTicket(String customer, Long date, double totalSek, ServiceType service){
        this.customer = customer;
        this.date = date;
        this.totalSek = totalSek;
        this.service = service;
    }

	public Double getTotalSek() {
		return totalSek;
	}

	public void setTotalSek(Double totalSek) {
		this.totalSek = totalSek;
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

	public ServiceType getService() {
		return service;
	}

	public void setService(ServiceType serviceType) {
		this.service = serviceType;
	}

	// public Technician getTechnician() {
	// 	return technician;
	// }
	//
	// public void setTechnician(Technician technician) {
	// 	this.technician = technician;
	// }

	public Boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}
}
