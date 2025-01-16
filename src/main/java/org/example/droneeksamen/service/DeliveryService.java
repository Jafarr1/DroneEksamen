package org.example.droneeksamen.service;

import org.example.droneeksamen.model.Drone;
import org.example.droneeksamen.model.Delivery;
import org.example.droneeksamen.model.Pizza;
import org.example.droneeksamen.model.Status;
import org.example.droneeksamen.repository.DeliveryRepository;
import org.example.droneeksamen.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DroneRepository droneRepository;

    // Endpoint: /deliveries
    public List<Delivery> getAllActiveDeliveries() {
        return deliveryRepository.findByActualDeliveryTimeIsNull();  // Get deliveries that haven't been delivered
    }

    // Endpoint: /deliveries/add
    public Delivery addDelivery(Pizza pizza) {
        Delivery newDelivery = new Delivery();
        newDelivery.setPizza(pizza);
        newDelivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));  // Expected delivery time is 30 minutes from now
        newDelivery.setActualDeliveryTime(null);  // Set actual delivery time to null
        newDelivery.setDrone(null);  // No drone assigned yet
        return deliveryRepository.save(newDelivery);
    }

    // Endpoint: /deliveries/queue
    public List<Delivery> getPendingDeliveries() {
        return deliveryRepository.findByDroneIsNull();  // Get deliveries without a drone
    }

    // Endpoint: /deliveries/schedule
    public Delivery scheduleDelivery(Long deliveryId, String droneUuid) throws Exception {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery not found"));

        if (delivery.getDrone() != null) {
            throw new Exception("This delivery already has a drone assigned.");
        }

        Drone drone = droneRepository.findBySerialUuid(droneUuid)
                .orElseThrow(() -> new Exception("Drone not found"));

        // Check if the drone is in operational status (I_DRIFT)
        if (drone.getStatus() != Status.I_DRIFT) {
            throw new Exception("Drone is not in operational status.");
        }

        delivery.setDrone(drone);
        deliveryRepository.save(delivery);
        return delivery;
    }

    // Endpoint: /deliveries/finish
    public Delivery finishDelivery(Long deliveryId) throws Exception {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery not found"));

        if (delivery.getDrone() == null) {
            throw new Exception("This delivery does not have a drone assigned.");
        }

        // Mark the delivery as finished (set actual delivery time)
        delivery.setActualDeliveryTime(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }
}
