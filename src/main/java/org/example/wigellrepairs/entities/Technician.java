package org.example.wigellrepairs.entities;

import org.example.wigellrepairs.enums.Expertise;
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
