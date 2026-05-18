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
        BikeDTO bike = new BikeDTO("BIKE-002", "Bob Lindqvist");
        repairShop.registerBike(bike);
        repairShop.addTask(new TaskDTO("Battery Check", new Amount(200)));
        RepairOrder repairOrder = repairShop.endRepair();
        assertEquals("BIKE-002", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectTotal() {
        repairShop.registerBike(new BikeDTO("BIKE-002", "Bob Lindqvist"));
        repairShop.addTask(new TaskDTO("Battery Check", new Amount(200)));
        repairShop.addTask(new TaskDTO("Chain Lubrication", new Amount(150)));
        RepairOrder repairOrder = repairShop.endRepair();
        assertEquals(350, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void startRepairTwiceCreatesNewSessionWithZeroTotal() {
        repairShop.registerBike(new BikeDTO("BIKE-001", "Alice Svensson"));
        repairShop.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        repairShop.startRepair();
        repairShop.registerBike(new BikeDTO("BIKE-002", "Bob Lindqvist"));
        RepairOrder repairOrder = repairShop.endRepair();
        assertEquals(0, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsReflectedInRepairOrder() {
        repairShop.registerBike(new BikeDTO("BIKE-003", "Carl Johansson"));
        repairShop.enterDiagnosticReport("Tire replaced successfully.");
        RepairOrder repairOrder = repairShop.endRepair();
        assertEquals("Tire replaced successfully.", repairOrder.getDiagnosticReport());
    }
}
