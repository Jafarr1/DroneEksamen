package org.example.droneeksamen.model;

import jakarta.persistence.*;

@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long droneId;

    @Column(unique = true)
    private String serialUuid;

    @Enumerated(EnumType.STRING)
    private Status driftsstatus;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;


    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public String getSerialUuid() {
        return serialUuid;
    }

    public void setSerialUuid(String serialUuid) {
        this.serialUuid = serialUuid;
    }

    public Status getDriftsstatus() {
        return driftsstatus;
    }

    public void setDriftsstatus(Status driftsstatus) {
        this.driftsstatus = driftsstatus;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}

