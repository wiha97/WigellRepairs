package org.example.wigellrepairs.dtos.services;

import java.io.Serializable;
import java.util.List;

import org.example.wigellrepairs.deserializers.ExpertiseDeserializer;
import org.example.wigellrepairs.dtos.technicians.CreateTechnicianDTO;
import org.example.wigellrepairs.dtos.technicians.MinimalTechnicianDTO;
import org.example.wigellrepairs.enums.Expertise;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.annotation.Nullable;

public class CreateServiceTypeDTO implements Serializable {
    protected String name;
    protected Double sek;
    @JsonDeserialize(using = ExpertiseDeserializer.class)
    protected Expertise category;
    @Nullable
    protected List<MinimalTechnicianDTO> technician;

    public CreateServiceTypeDTO() {
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSek() {
		return sek;
	}

	public void setSek(Double sek) {
		this.sek = sek;
	}

	public Expertise getCategory() {
		return category;
	}

	public void setCategory(Expertise category) {
		this.category = category;
	}


	public List<MinimalTechnicianDTO> getTechnician() {
		return technician;
	}


	public void setTechnician(List<MinimalTechnicianDTO> technician) {
		this.technician = technician;
	}
}
