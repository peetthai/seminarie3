package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveRepairTest {

    private ActiveRepair activeRepair;
    private BikeDTO sampleBike;

    @BeforeEach
    void setUp() {
        activeRepair = new ActiveRepair();
        sampleBike = new BikeDTO("BIKE-001",
                new CustomerDTO("CUST-001", "Alice Svensson", "070-111 11 11"));
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
        activeRepair.registerBike(sampleBike);
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-1");
        assertEquals("BIKE-001", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairStampsTheGivenRepairOrderId() {
        activeRepair.registerBike(sampleBike);
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-42");
        assertEquals("RO-42", repairOrder.getRepairOrderID());
    }

    @Test
    void endRepairAfterAddingTasksReturnsAllTasksInOrder() {
        activeRepair.registerBike(sampleBike);
        activeRepair.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        activeRepair.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-1");
        assertEquals(2, repairOrder.getTasks().size());
    }

    @Test
    void endRepairReturnsCorrectTotalInRepairOrder() {
        activeRepair.registerBike(sampleBike);
        activeRepair.addTask(new TaskDTO("Brake Pad Replacement", new Amount(350)));
        activeRepair.addTask(new TaskDTO("Tire Replacement", new Amount(500)));
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-1");
        assertEquals(850, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void endRepairWithoutTasksReturnsTotalOfZero() {
        activeRepair.registerBike(sampleBike);
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-1");
        assertEquals(0, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsIncludedInRepairOrder() {
        activeRepair.registerBike(sampleBike);
        String report = "Front tire replaced.";
        activeRepair.enterDiagnosticReport(report);
        RepairOrderDTO repairOrder = activeRepair.endRepair("RO-1");
        assertEquals(report, repairOrder.getDiagnosticReport());
    }
}
