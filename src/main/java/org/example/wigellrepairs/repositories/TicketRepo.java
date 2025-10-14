package org.example.wigellrepairs.repositories;

import java.util.List;

import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.enums.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<RepairTicket, Long> {
    List<RepairTicket> findByCustomerContaining(String customer);
    List<RepairTicket> findByServiceCategory(Expertise category);
}
