package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepairShopTest {

    private RepairShop repairShop;

    @BeforeEach
    void setUp() {
        repairShop = new RepairShop();
        repairShop.startRepair();
    }

    private BikeDTO bike(String bikeID, String customerID, String customerName) {
        return new BikeDTO(bikeID, new CustomerDTO(customerID, customerName, "070-000 00 00"));
    }

    @Test
    void addTaskAfterStartReturnsTaskCostAsRunningTotal() {
        TaskDTO task = new TaskDTO("Brake Pad Replacement", new Amount(350));
        Amount runningTotal = repairShop.addTask(task);
        assertEquals(350, runningTotal.getValue(), 0.001);
    }

    @Test
    void addTwoTasksReturnsCumulativeTotal() {
        repairShop.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        Amount runningTotal = repairShop.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        assertEquals(850, runningTotal.getValue(), 0.001);
    }

    @Test
    void endRepairAfterRegisterBikeAndTasksReturnsRepairOrderWithBike() {
        repairShop.registerBike(bike("BIKE-002", "CUST-002", "Bob Lindqvist"));
        repairShop.addTask(new TaskDTO("Battery Check", new Amount(200)));
        RepairOrderDTO repairOrder = repairShop.endRepair();
        assertEquals("BIKE-002", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectTotal() {
        repairShop.registerBike(bike("BIKE-002", "CUST-002", "Bob Lindqvist"));
        repairShop.addTask(new TaskDTO("Battery Check", new Amount(200)));
        repairShop.addTask(new TaskDTO("Chain Lubrication", new Amount(150)));
        RepairOrderDTO repairOrder = repairShop.endRepair();
        assertEquals(350, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void endRepairAssignsANonNullRepairOrderId() {
        repairShop.registerBike(bike("BIKE-001", "CUST-001", "Alice Svensson"));
        RepairOrderDTO repairOrder = repairShop.endRepair();
        assertNotNull(repairOrder.getRepairOrderID());
    }

    @Test
    void twoConsecutiveRepairsGetDifferentRepairOrderIds() {
        repairShop.registerBike(bike("BIKE-001", "CUST-001", "Alice Svensson"));
        RepairOrderDTO first = repairShop.endRepair();
        repairShop.startRepair();
        repairShop.registerBike(bike("BIKE-002", "CUST-002", "Bob Lindqvist"));
        RepairOrderDTO second = repairShop.endRepair();
        assertNotEquals(first.getRepairOrderID(), second.getRepairOrderID());
    }

    @Test
    void startRepairTwiceCreatesNewSessionWithZeroTotal() {
        repairShop.registerBike(bike("BIKE-001", "CUST-001", "Alice Svensson"));
        repairShop.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        repairShop.startRepair();
        repairShop.registerBike(bike("BIKE-002", "CUST-002", "Bob Lindqvist"));
        RepairOrderDTO repairOrder = repairShop.endRepair();
        assertEquals(0, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsReflectedInRepairOrder() {
        repairShop.registerBike(bike("BIKE-003", "CUST-003", "Carl Johansson"));
        repairShop.enterDiagnosticReport("Tire replaced successfully.");
        RepairOrderDTO repairOrder = repairShop.endRepair();
        assertEquals("Tire replaced successfully.", repairOrder.getDiagnosticReport());
    }
}
