package view;

import controller.Controller;
import model.Amount;
import model.BikeDTO;
import model.CustomerDTO;
import model.RepairOrderDTO;
import model.TaskDTO;

import java.util.List;

/**
 * Placeholder view that simulates mechanic interactions using hard-coded method calls.
 * Everything returned by the controller is printed to standard output.
 */
public class View {
    private final Controller controller;

    /**
     * Creates a View connected to the given controller.
     *
     * @param controller The controller to use for all system operations.
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Simulates a complete repair session and the two follow-up searches using
     * hard-coded inputs, printing all output returned by the controller to
     * standard output.
     */
    public void runFakeExecution() {
        System.out.println("=== Repair Electric Bike System ===");

        RepairOrderDTO repairOrder = registerNewRepair();
        searchForSavedRepairOrder(repairOrder.getRepairOrderID());
        searchForCustomer("CUST-001");
    }

    private RepairOrderDTO registerNewRepair() {
        System.out.println();
        System.out.println("--- Use case: register a new repair ---");

        controller.startNewRepair();
        System.out.println("[Mechanic] Started new repair session.");

        BikeDTO bike = controller.enterBikeID("BIKE-001");
        if (bike != null) {
            CustomerDTO customer = bike.getCustomer();
            System.out.println("[System]   Bike registered: ID=" + bike.getBikeID()
                    + ", Customer=" + customer.getName() + " (" + customer.getCustomerID() + ")");
        } else {
            System.out.println("[System]   Bike not found.");
        }

        String[] taskNames = {"Brake Pad Replacement", "Tire Replacement"};
        for (String taskName : taskNames) {
            Amount runningTotal = controller.addRepairTask(taskName);
            if (runningTotal != null) {
                System.out.println("[System]   Added task: \"" + taskName
                        + "\" | Running total: " + runningTotal);
            } else {
                System.out.println("[System]   Task not found: \"" + taskName + "\"");
            }
        }

        String diagnosticNotes = "Front brake pads fully worn. Front tire flat (puncture). "
                + "Both parts replaced. Bike tested and approved.";
        controller.enterDiagnosticReport(diagnosticNotes);
        System.out.println("[Mechanic] Diagnostic report entered.");

        RepairOrderDTO repairOrder = controller.endRepair();
        printRepairOrder(repairOrder);
        System.out.println("[System]   Repair order " + repairOrder.getRepairOrderID()
                + " saved to the repair order registry.");
        return repairOrder;
    }

    private void searchForSavedRepairOrder(String repairOrderID) {
        System.out.println();
        System.out.println("--- Use case: search for a saved repair order ---");
        System.out.println("[Mechanic] Searching for repair order \"" + repairOrderID + "\"...");

        RepairOrderDTO found = controller.findRepairOrder(repairOrderID);
        if (found != null) {
            printRepairOrder(found);
        } else {
            System.out.println("[System]   No repair order found with ID \"" + repairOrderID + "\".");
        }
    }

    private void searchForCustomer(String customerID) {
        System.out.println();
        System.out.println("--- Use case: search for a customer ---");
        System.out.println("[Mechanic] Searching for customer \"" + customerID + "\"...");

        CustomerDTO customer = controller.findCustomer(customerID);
        if (customer != null) {
            System.out.println("[System]   Customer found: " + customer.getCustomerID()
                    + ", " + customer.getName() + ", " + customer.getPhoneNumber());
        } else {
            System.out.println("[System]   No customer found with ID \"" + customerID + "\".");
            return;
        }

        List<RepairOrderDTO> history = controller.findRepairOrdersForCustomer(customerID);
        System.out.println("[System]   Repair orders on file for this customer: " + history.size());
        for (RepairOrderDTO order : history) {
            System.out.println("             " + order.getRepairOrderID()
                    + " | total " + order.getTotal());
        }
    }

    private void printRepairOrder(RepairOrderDTO repairOrder) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("              REPAIR ORDER              ");
        System.out.println("========================================");
        System.out.println("Order ID: " + repairOrder.getRepairOrderID());
        System.out.println("Bike ID : " + repairOrder.getBike().getBikeID());
        System.out.println("Customer: " + repairOrder.getBike().getCustomer().getName());
        System.out.println("----------------------------------------");
        System.out.println("Repair Tasks:");
        for (TaskDTO task : repairOrder.getTasks()) {
            System.out.printf("  %-28s %s%n", task.getName(), task.getCost());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total   : " + repairOrder.getTotal());
        System.out.println("----------------------------------------");
        System.out.println("Diagnostic Report:");
        System.out.println("  " + repairOrder.getDiagnosticReport());
        System.out.println("========================================");
    }
}
