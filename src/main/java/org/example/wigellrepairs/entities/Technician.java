package org.example.wigellrepairs.entities;

import java.util.List;

import org.example.wigellrepairs.enums.Expertise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_id")
    private Long id;

    @Column(name = "tech_name", length = 35)
    private String name;

    @Column(name = "expertise")
    @Enumerated(EnumType.STRING)
    private Expertise expertise;

    // @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    // @JoinColumn(name = "service_id", nullable = true)
    // @ManyToMany
    // @JoinTable(name = "tech_service", joinColumns = @JoinColumn(name = "service_id"),
    //     inverseJoinColumns = @JoinColumn(name = "tech_id"))
    // private ServiceType service;

	// @OneToMany(mappedBy = "technician", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	//    @JsonIgnoreProperties("technician")
	//    private List<RepairTicket> repairTickets;

	public Technician() {
    }

    public Technician(String name, Expertise expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public Expertise getExpertise() {
		return expertise;
	}

    public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}

	// public List<RepairTicket> getRepairTickets() {
	// 	return repairTickets;
	// }
	//
	// public void setRepairTickets(List<RepairTicket> repairTickets) {
	// 	this.repairTickets = repairTickets;
	// }

	// public ServiceType getService() {
	// 	return service;
	// }
	//
	// public void setService(ServiceType service) {
	// 	this.service = service;
	// }

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
}
