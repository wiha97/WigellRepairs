package org.example.wigellrepairs.dtos.technicians;

import org.example.wigellrepairs.enums.Expertise;

public class ShowTechnicianDTO {

    private Long id;
    private String name;
    private Expertise expertise;

    public ShowTechnicianDTO() {
    }

    public ShowTechnicianDTO(Long id, String name, Expertise expertise) {
        this.id = id;
        this.name = name;
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
	public Expertise getExpertise() {
		return expertise;
	}
	public void setExpertise(Expertise expertise) {
		this.expertise = expertise;
	}

}
