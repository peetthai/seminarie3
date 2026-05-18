package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveRepairTest {

    private ActiveRepair activeRepair;

    @BeforeEach
    void setUp() {
        activeRepair = new ActiveRepair();
    }

    @Test
    void addSingleTaskReturnsTaskCostAsRunningTotal() {
        TaskDTO task = new TaskDTO("Brake Pad Replacement", new Amount(350));
        Amount runningTotal = activeRepair.addTask(task);
        assertEquals(350, runningTotal.getValue(), 0.001);
    }

    @Test
    void addTwoTasksReturnsCorrectCumulativeTotal() {
        activeRepair.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        Amount runningTotal = activeRepair.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        assertEquals(850, runningTotal.getValue(), 0.001);
    }

    @Test
    void endRepairWithRegisteredBikeReturnsBikeInRepairOrder() {
        BikeDTO bike = new BikeDTO("BIKE-001", "Alice Svensson");
        activeRepair.registerBike(bike);
        RepairOrder repairOrder = activeRepair.endRepair();
        assertEquals("BIKE-001", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairAfterAddingTasksReturnsAllTasksInOrder() {
        activeRepair.registerBike(new BikeDTO("BIKE-001", "Alice Svensson"));
        activeRepair.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        activeRepair.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        RepairOrder repairOrder = activeRepair.endRepair();
        assertEquals(2, repairOrder.getTasks().size());
    }

    @Test
    void endRepairReturnsCorrectTotalInRepairOrder() {
        activeRepair.registerBike(new BikeDTO("BIKE-001", "Alice Svensson"));
        activeRepair.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        activeRepair.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        RepairOrder repairOrder = activeRepair.endRepair();
        assertEquals(850, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void endRepairWithoutTasksReturnsTotalOfZero() {
        activeRepair.registerBike(new BikeDTO("BIKE-001", "Alice Svensson"));
        RepairOrder repairOrder = activeRepair.endRepair();
        assertEquals(0, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsIncludedInRepairOrder() {
        activeRepair.registerBike(new BikeDTO("BIKE-001", "Alice Svensson"));
        String report = "Front tire replaced.";
        activeRepair.enterDiagnosticReport(report);
        RepairOrder repairOrder = activeRepair.endRepair();
        assertEquals(report, repairOrder.getDiagnosticReport());
    }
}
