package org.example.wigellrepairs.dtos.services;

public class EditServiceTypeDTO extends CreateServiceTypeDTO {
    private Long id;

    public EditServiceTypeDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
