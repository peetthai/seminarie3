package integration;

import model.BikeDTO;
import model.CustomerDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles retrieval of bike information from persistent storage.
 * In this implementation, bikes are stored in memory as sample data.
 */
public class BikeRegistry {
    private final Map<String, BikeDTO> bikes = new HashMap<>();

    /**
     * Creates a BikeRegistry pre-loaded with sample bike records, each linked to
     * the customer who owns it.
     */
    public BikeRegistry() {
        bikes.put("BIKE-001", new BikeDTO("BIKE-001",
                new CustomerDTO("CUST-001", "Alice Svensson", "070-111 11 11")));
        bikes.put("BIKE-002", new BikeDTO("BIKE-002",
                new CustomerDTO("CUST-002", "Bob Lindqvist", "070-222 22 22")));
        bikes.put("BIKE-003", new BikeDTO("BIKE-003",
                new CustomerDTO("CUST-003", "Carl Johansson", "070-333 33 33")));
    }

    /**
     * Looks up and returns the bike with the given ID.
     *
     * @param bikeID The unique identifier of the bike to look up.
     * @return The {@link BikeDTO} for the found bike, or {@code null} if no bike
     *         with the given ID exists in the registry.
     */
    public BikeDTO findBike(String bikeID) {
        return bikes.get(bikeID);
    }
}
