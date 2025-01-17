package org.example.droneeksamen.service;

import org.example.droneeksamen.model.Drone;
import org.example.droneeksamen.model.Delivery;
import org.example.droneeksamen.model.Pizza;
import org.example.droneeksamen.model.Status;
import org.example.droneeksamen.repository.DeliveryRepository;
import org.example.droneeksamen.repository.DroneRepository;
import org.example.droneeksamen.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PizzaRepository pizzaRepository;


    public List<Delivery> getAllActiveDeliveries() {
        return deliveryRepository.findByActualDeliveryTimeIsNull();  // Get deliveries that haven't been delivered
    }


    public Delivery addDelivery(Long pizzaId) throws Exception {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new Exception("Pizza not found with id: " + pizzaId));



        Delivery delivery = new Delivery();
        delivery.setPizza(pizza);
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        delivery.setActualDeliveryTime(null);
        delivery.setDrone(null);


        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getPendingDeliveries() {
        return deliveryRepository.findByDroneIsNull();
    }

    public Delivery scheduleDelivery(Long deliveryId, String serialUuid) throws Exception {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery not found with ID: " + deliveryId));


        if (delivery.getDrone() != null) {
            throw new Exception("Delivery already has a drone assigned.");
        }


        Drone drone = droneRepository.findBySerialUuid(serialUuid)
                .orElseThrow(() -> new Exception("Drone not found with serialUuid: " + serialUuid));


        if (drone.getDriftsstatus() != Status.I_DRIFT) {
            throw new Exception("Drone is not operational.");
        }


        delivery.setDrone(drone);


        return deliveryRepository.save(delivery);
    }






    public Delivery finishDelivery(Long deliveryId) throws Exception {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery not found"));

        if (delivery.getDrone() == null) {
            throw new Exception("This delivery does not have a drone assigned.");
        }


        delivery.setActualDeliveryTime(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }
}
