package org.example.wigellrepairs.dtos.technicians;

import org.example.wigellrepairs.enums.Expertise;

public class CreateTechnicianDTO {
    private String name;
    private Expertise expertise;

    public CreateTechnicianDTO() {
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
