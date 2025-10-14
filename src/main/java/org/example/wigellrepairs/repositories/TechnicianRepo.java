package org.example.wigellrepairs.repositories;

import org.example.wigellrepairs.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepo extends JpaRepository<Technician, Long> {
    boolean existsByNameIgnoreCase(String name);
}
