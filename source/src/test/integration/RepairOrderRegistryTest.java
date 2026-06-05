package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Amount;
import model.BikeDTO;
import model.CustomerDTO;
import model.RepairOrderDTO;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepairOrderRegistryTest {

    private RepairOrderRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new RepairOrderRegistry();
    }

    private RepairOrderDTO order(String repairOrderID, String customerID) {
        BikeDTO bike = new BikeDTO("BIKE-X",
                new CustomerDTO(customerID, "Sample Customer", "070-000 00 00"));
        return new RepairOrderDTO(repairOrderID, bike, Collections.emptyList(),
                "report", new Amount(100));
    }

    @Test
    void saveThenFindByIdReturnsTheSavedOrder() {
        RepairOrderDTO saved = order("RO-1", "CUST-001");
        registry.saveRepairOrder(saved);
        RepairOrderDTO found = registry.findRepairOrderByID("RO-1");
        assertEquals("RO-1", found.getRepairOrderID());
    }

    @Test
    void findByIdWithUnknownIdReturnsNull() {
        registry.saveRepairOrder(order("RO-1", "CUST-001"));
        assertNull(registry.findRepairOrderByID("RO-999"));
    }

    @Test
    void findByIdOnEmptyRegistryReturnsNull() {
        assertNull(registry.findRepairOrderByID("RO-1"));
    }

    @Test
    void saveWithExistingIdOverwritesThePreviousOrder() {
        registry.saveRepairOrder(order("RO-1", "CUST-001"));
        registry.saveRepairOrder(order("RO-1", "CUST-002"));
        RepairOrderDTO found = registry.findRepairOrderByID("RO-1");
        assertEquals("CUST-002", found.getBike().getCustomer().getCustomerID());
    }

    @Test
    void findByCustomerReturnsOnlyThatCustomersOrders() {
        registry.saveRepairOrder(order("RO-1", "CUST-001"));
        registry.saveRepairOrder(order("RO-2", "CUST-002"));
        registry.saveRepairOrder(order("RO-3", "CUST-001"));
        List<RepairOrderDTO> result = registry.findRepairOrdersByCustomer("CUST-001");
        assertEquals(2, result.size());
    }

    @Test
    void findByCustomerWithNoMatchesReturnsEmptyList() {
        registry.saveRepairOrder(order("RO-1", "CUST-001"));
        List<RepairOrderDTO> result = registry.findRepairOrdersByCustomer("CUST-999");
        assertTrue(result.isEmpty());
    }
}
