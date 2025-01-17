package org.example.droneeksamen.service;

import org.example.droneeksamen.model.Drone;
import org.example.droneeksamen.model.Status;
import org.example.droneeksamen.model.Station;
import org.example.droneeksamen.repository.DroneRepository;
import org.example.droneeksamen.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private StationRepository stationRepository;

    public List<Drone> getAllDronesWithDetails() {
        return droneRepository.findAll()
                .stream()
                .map(drone -> {
                    drone.setStation(drone.getStation());
                    return drone;
                })
                .collect(Collectors.toList());
    }

    public void registerNewDrone() throws Exception {
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            throw new Exception("No stations available.");
        }

        Station selectedStation = stations.stream()
                .min((s1, s2) -> {
                    long count1 = droneRepository.findByStation(s1).size();
                    long count2 = droneRepository.findByStation(s2).size();
                    return Long.compare(count1, count2);
                })
                .orElseThrow();

        Drone newDrone = new Drone();
        newDrone.setSerialUuid(UUID.randomUUID().toString());
        newDrone.setDriftsstatus(Status.I_DRIFT);
        newDrone.setStation(selectedStation);

        droneRepository.save(newDrone);
    }

    public void changeDroneStatus(String serialUuid, String status) throws Exception {
        Drone drone = droneRepository.findBySerialUuid(serialUuid)
                .orElseThrow(() -> new Exception("Drone not found with Serial UUID: " + serialUuid));
        switch (status.toLowerCase()) {
            case "i drift":
                drone.setDriftsstatus(Status.I_DRIFT);
                break;
            case "ude af drift":
                drone.setDriftsstatus(Status.UDE_AF_DRIFT);
                break;
            case "udfaset":
                drone.setDriftsstatus(Status.UDFASET);
                break;
            default:
                throw new Exception("Invalid status: " + status);
        }
        droneRepository.save(drone);
    }
}
