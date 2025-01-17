package org.example.droneeksamen.repository;

import org.example.droneeksamen.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByActualDeliveryTimeIsNull();

    List<Delivery> findByDroneIsNull();
}
