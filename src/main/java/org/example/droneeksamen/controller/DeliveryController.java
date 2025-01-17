package org.example.droneeksamen.controller;

import org.example.droneeksamen.model.Delivery;
import org.example.droneeksamen.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins ="*")
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAllActiveDeliveries() {
        return deliveryService.getAllActiveDeliveries();
    }


    @PostMapping("/add")
    public ResponseEntity<Delivery> addDelivery(@RequestParam Long pizzaId) {
        try {
            Delivery newDelivery = deliveryService.addDelivery(pizzaId);
            return ResponseEntity.ok(newDelivery);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/queue")
    public List<Delivery> getPendingDeliveries() {
        return deliveryService.getPendingDeliveries();
    }


    @PostMapping("/schedule/{deliveryId}/{serialUuid}")
    public Delivery scheduleDelivery(@PathVariable Long deliveryId, @PathVariable String serialUuid) throws Exception {
        return deliveryService.scheduleDelivery(deliveryId, serialUuid);
    }


    @PostMapping("/finish/{deliveryId}")
    public Delivery finishDelivery(@PathVariable Long deliveryId) throws Exception {
        return deliveryService.finishDelivery(deliveryId);
    }
}
