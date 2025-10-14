package org.example.wigellrepairs.dtos.services;

import java.util.List;

import org.example.wigellrepairs.dtos.technicians.ShowTechnicianDTO;
import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.enums.Expertise;

public class ShowServiceAdminDTO extends ShowServiceTypeDTO {

    private List<ShowTechnicianDTO> technicians;

    public ShowServiceAdminDTO(Long id, String name, double sek, double eur, Expertise category, List<ShowTechnicianDTO> technicians) {
        this.id = id;
        this.name = name;
        this.sek = sek;
        this.eur = eur;
        this.category = category;
        this.technicians = technicians;
    }

	public List<ShowTechnicianDTO> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<ShowTechnicianDTO> technicians) {
		this.technicians = technicians;
	}
}
