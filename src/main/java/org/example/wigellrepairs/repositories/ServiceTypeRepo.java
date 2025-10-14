package org.example.wigellrepairs.repositories;

import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.enums.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long> {
    boolean existsByNameIgnoreCase(String name);
    ServiceType findByNameIgnoreCase(String name);
    ServiceType findByCategory(Expertise category);
}
