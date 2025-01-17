package org.example.droneeksamen.controller;

import org.example.droneeksamen.model.Drone;
import org.example.droneeksamen.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin (origins ="*")
@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @GetMapping
    public List<Drone> getAllDrones() {
        return droneService.getAllDronesWithDetails();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDrone() {
        try {
            droneService.registerNewDrone();
            return ResponseEntity.ok("Drone successfully added.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add drone: " + e.getMessage());
        }
    }


    // FOR AT ÆNDRE DRONE STATUS POST /drones/disable?serialUuid=Uuid nummeret
    @PostMapping("/enable")
    public ResponseEntity<String> enableDrone(@RequestParam String serialUuid) {
        return changeDroneStatus(serialUuid, "i drift", "enabled");
    }

    @PostMapping("/disable")
    public ResponseEntity<String> disableDrone(@RequestParam String serialUuid) {
        return changeDroneStatus(serialUuid, "ude af drift", "disabled");
    }

    @PostMapping("/retire")
    public ResponseEntity<String> retireDrone(@RequestParam String serialUuid) {
        return changeDroneStatus(serialUuid, "udfaset", "retired");
    }

    private ResponseEntity<String> changeDroneStatus(String serialUuid, String status, String action) {
        try {
            droneService.changeDroneStatus(serialUuid, status);
            return ResponseEntity.ok("Drone successfully " + action + ".");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update drone: " + e.getMessage());
        }
    }
}
