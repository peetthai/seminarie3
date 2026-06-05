package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.BikeDTO;

import static org.junit.jupiter.api.Assertions.*;

class BikeRegistryTest {

    private BikeRegistry bikeRegistry;

    @BeforeEach
    void setUp() {
        bikeRegistry = new BikeRegistry();
    }

    @Test
    void findBikeWithExistingIdReturnsBikeDTO() {
        BikeDTO bike = bikeRegistry.findBike("BIKE-001");
        assertNotNull(bike);
    }

    @Test
    void findBikeWithExistingIdReturnsCorrectBikeId() {
        BikeDTO bike = bikeRegistry.findBike("BIKE-001");
        assertEquals("BIKE-001", bike.getBikeID());
    }

    @Test
    void findBikeWithExistingIdReturnsCorrectCustomerName() {
        BikeDTO bike = bikeRegistry.findBike("BIKE-002");
        assertEquals("Bob Lindqvist", bike.getCustomer().getName());
    }

    @Test
    void findBikeWithNonExistingIdReturnsNull() {
        BikeDTO bike = bikeRegistry.findBike("BIKE-999");
        assertNull(bike);
    }

    @Test
    void findBikeWithEmptyStringReturnsNull() {
        BikeDTO bike = bikeRegistry.findBike("");
        assertNull(bike);
    }

    @Test
    void findBikeWithAllThreeSampleBikesReturnsNonNull() {
        assertNotNull(bikeRegistry.findBike("BIKE-001"));
        assertNotNull(bikeRegistry.findBike("BIKE-002"));
        assertNotNull(bikeRegistry.findBike("BIKE-003"));
    }
}
