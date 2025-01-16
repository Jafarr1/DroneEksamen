package org.example.droneeksamen.repository;

import org.example.droneeksamen.model.Drone;
import org.example.droneeksamen.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialUuid(String serialUuid);
    List<Drone> findByStation(Station station);

}
