package com.ds.avengers.repo;

import com.ds.avengers.dto.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDeliveryAgentRepo extends JpaRepository<DeliveryAgent, Long> {
    @Query("select d FROM DeliveryAgent d WHERE d.name = ?1")
    DeliveryAgent findByDeliveryAgentName(String name);

    @Query("select d from DeliveryAgent d where d.status='AVAILABLE'")
    List<DeliveryAgent> availableDeliveryAgents();

    @Modifying
    @Query("update DeliveryAgent d set d.status = :status, d.phone = :phone where d.id = :id")
    void updateAgent(@Param("phone") String phone, @Param("status") String status, @Param("id") Long id);

}
