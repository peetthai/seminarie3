package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import integration.BikeRegistry;
import integration.RepairTaskCatalog;
import model.Amount;
import model.BikeDTO;
import model.RepairOrder;
import model.RepairShop;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        BikeRegistry bikeRegistry = new BikeRegistry();
        RepairTaskCatalog taskCatalog = new RepairTaskCatalog();
        RepairShop repairShop = new RepairShop();
        controller = new Controller(repairShop, bikeRegistry, taskCatalog);
        controller.startNewRepair();
    }

    @Test
    void enterBikeIdWithValidIdReturnsNonNullBikeDTO() {
        BikeDTO bike = controller.enterBikeID("BIKE-001");
        assertNotNull(bike);
    }

    @Test
    void enterBikeIdWithValidIdReturnsCorrectBikeId() {
        BikeDTO bike = controller.enterBikeID("BIKE-001");
        assertEquals("BIKE-001", bike.getBikeID());
    }

    @Test
    void enterBikeIdWithUnknownIdReturnsNull() {
        BikeDTO bike = controller.enterBikeID("BIKE-999");
        assertNull(bike);
    }

    @Test
    void addRepairTaskWithValidNameReturnsNonNullAmount() {
        controller.enterBikeID("BIKE-001");
        Amount total = controller.addRepairTask("Brake Pad Replacement");
        assertNotNull(total);
    }

    @Test
    void addRepairTaskWithValidNameReturnsCorrectRunningTotal() {
        controller.enterBikeID("BIKE-001");
        Amount total = controller.addRepairTask("Brake Pad Replacement");
        assertEquals(350, total.getValue(), 0.001);
    }

    @Test
    void addTwoRepairTasksReturnsCumulativeTotal() {
        controller.enterBikeID("BIKE-001");
        controller.addRepairTask("Brake Pad Replacement");
        Amount total = controller.addRepairTask("Tire Replacement");
        assertEquals(850, total.getValue(), 0.001);
    }

    @Test
    void addRepairTaskWithUnknownNameReturnsNull() {
        controller.enterBikeID("BIKE-001");
        Amount total = controller.addRepairTask("Non-existing Task");
        assertNull(total);
    }

    @Test
    void endRepairReturnsNonNullRepairOrder() {
        controller.enterBikeID("BIKE-001");
        controller.addRepairTask("Battery Check");
        RepairOrder repairOrder = controller.endRepair();
        assertNotNull(repairOrder);
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectBikeId() {
        controller.enterBikeID("BIKE-002");
        RepairOrder repairOrder = controller.endRepair();
        assertEquals("BIKE-002", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectTotalAfterTasks() {
        controller.enterBikeID("BIKE-001");
        controller.addRepairTask("Battery Check");
        controller.addRepairTask("Chain Lubrication");
        RepairOrder repairOrder = controller.endRepair();
        assertEquals(350, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsReflectedInFinalRepairOrder() {
        controller.enterBikeID("BIKE-003");
        controller.enterDiagnosticReport("Chain replaced and tested.");
        RepairOrder repairOrder = controller.endRepair();
        assertEquals("Chain replaced and tested.", repairOrder.getDiagnosticReport());
    }
}
