package org.example.wigellrepairs.dtos.services;

import java.io.Serializable;
import org.example.wigellrepairs.enums.Expertise;

public class ShowServiceTypeDTO implements Serializable {

    protected Long id;
    protected String name;
    protected Double sek;
    protected Double eur;
    protected Expertise category;

    public ShowServiceTypeDTO() {
    }

	public ShowServiceTypeDTO(Long id, String name, double sek, double eur, Expertise category) {
        this.id = id;
        this.name = name;
        this.sek = sek;
        this.eur = eur;
        this.category = category;
    }

	public String getName() {
		return name;
	}

	public Double getEur() {
		return eur;
	}

	public void setEur(Double eur) {
		this.eur = eur;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
