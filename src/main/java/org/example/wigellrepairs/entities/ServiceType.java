package org.example.wigellrepairs.entities;

import org.example.wigellrepairs.enums.Expertise;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "repair_services")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name", length = 35, nullable = false)
    private String name;

    @Column(name = "service_price_sek", nullable = false)
    private double sek;

    @Column(name = "service_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Expertise category;

    @JsonIgnore
    @OneToMany(mappedBy = "service", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<RepairTicket> tickets;

    @ManyToMany
    @JoinTable(name = "tech_service", joinColumns = @JoinColumn(name = "tech_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Technician> technicians;

    public ServiceType() {
    }

    public ServiceType(String name, double sek, Expertise category, List<Technician> technicians) {
        this.name = name;
        this.sek = sek;
        this.category = category;
        this.technicians = technicians;
    }

	public List<Technician> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<Technician> technicians) {
		this.technicians = technicians;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RepairTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<RepairTicket> tickets) {
		this.tickets = tickets;
	}

	public double getSek() {
		return sek;
	}

	public void setSek(double sek) {
		this.sek = sek;
	}

	public Expertise getCategory() {
		return category;
	}

	public void setCategory(Expertise category) {
		this.category = category;
	}
}
