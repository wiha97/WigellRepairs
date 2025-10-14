package org.example.wigellrepairs.dtos.services;

import org.example.wigellrepairs.enums.Expertise;

public class MinimalServiceDTO {

    private Long id;
    private Expertise category;

    public MinimalServiceDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Expertise getCategory() {
		return category;
	}

	public void setCategory(Expertise category) {
		this.category = category;
	}
}
