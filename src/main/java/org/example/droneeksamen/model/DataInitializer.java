package org.example.droneeksamen.model;



import org.example.droneeksamen.repository.PizzaRepository;
import org.example.droneeksamen.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PizzaRepository pizzaRepository;
    private final StationRepository stationRepository;

    public DataInitializer(PizzaRepository pizzaRepository, StationRepository stationRepository) {
        this.pizzaRepository = pizzaRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize 3 stations close to Copenhagen
        if (stationRepository.count() < 3) {
            stationRepository.save(new Station(55.41, 12.34)); // Centrum af København
            stationRepository.save(new Station(55.42, 12.35)); // 1 km væk
            stationRepository.save(new Station(55.39, 12.32)); // 1 km væk
        }

        // Initialize 5 pizzas
        if (pizzaRepository.count() < 5) {
            pizzaRepository.save(new Pizza("Margherita", 90));
            pizzaRepository.save(new Pizza("Peperoni", 100));
            pizzaRepository.save(new Pizza("Hawaii", 95));
            pizzaRepository.save(new Pizza("Vegetar", 85));
            pizzaRepository.save(new Pizza("Quattro Stagioni", 110));
        }
    }
}

