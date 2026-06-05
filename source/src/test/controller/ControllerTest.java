package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import integration.RegistryCreator;
import model.Amount;
import model.BikeDTO;
import model.CustomerDTO;
import model.RepairOrderDTO;
import model.RepairShop;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        RegistryCreator registryCreator = new RegistryCreator();
        RepairShop repairShop = new RepairShop();
        controller = new Controller(repairShop, registryCreator);
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
        RepairOrderDTO repairOrder = controller.endRepair();
        assertNotNull(repairOrder);
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectBikeId() {
        controller.enterBikeID("BIKE-002");
        RepairOrderDTO repairOrder = controller.endRepair();
        assertEquals("BIKE-002", repairOrder.getBike().getBikeID());
    }

    @Test
    void endRepairReturnsRepairOrderWithCorrectTotalAfterTasks() {
        controller.enterBikeID("BIKE-001");
        controller.addRepairTask("Battery Check");
        controller.addRepairTask("Chain Lubrication");
        RepairOrderDTO repairOrder = controller.endRepair();
        assertEquals(350, repairOrder.getTotal().getValue(), 0.001);
    }

    @Test
    void enterDiagnosticReportIsReflectedInFinalRepairOrder() {
        controller.enterBikeID("BIKE-003");
        controller.enterDiagnosticReport("Chain replaced and tested.");
        RepairOrderDTO repairOrder = controller.endRepair();
        assertEquals("Chain replaced and tested.", repairOrder.getDiagnosticReport());
    }

    @Test
    void endRepairSavesRepairOrderSoItCanBeFoundById() {
        controller.enterBikeID("BIKE-001");
        controller.addRepairTask("Battery Check");
        RepairOrderDTO saved = controller.endRepair();
        RepairOrderDTO found = controller.findRepairOrder(saved.getRepairOrderID());
        assertEquals(saved.getRepairOrderID(), found.getRepairOrderID());
    }

    @Test
    void findRepairOrderWithUnknownIdReturnsNull() {
        assertNull(controller.findRepairOrder("RO-999"));
    }

    @Test
    void findCustomerWithExistingIdReturnsCorrectName() {
        CustomerDTO customer = controller.findCustomer("CUST-001");
        assertEquals("Alice Svensson", customer.getName());
    }

    @Test
    void findCustomerWithUnknownIdReturnsNull() {
        assertNull(controller.findCustomer("CUST-999"));
    }

    @Test
    void findRepairOrdersForCustomerReturnsSavedOrderForThatCustomer() {
        controller.enterBikeID("BIKE-001");
        controller.endRepair();
        List<RepairOrderDTO> history = controller.findRepairOrdersForCustomer("CUST-001");
        assertEquals(1, history.size());
    }

    @Test
    void findRepairOrdersForCustomerWithNoOrdersReturnsEmptyList() {
        List<RepairOrderDTO> history = controller.findRepairOrdersForCustomer("CUST-002");
        assertTrue(history.isEmpty());
    }
}
