package view;

import controller.Controller;
import model.Amount;
import model.BikeDTO;
import model.RepairOrder;
import model.TaskDTO;

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
     * Simulates a complete repair session using hard-coded inputs, printing all
     * output returned by the controller to standard output.
     */
    public void runFakeExecution() {
        System.out.println("=== Repair Electric Bike System ===");
        System.out.println();

        controller.startNewRepair();
        System.out.println("[Mechanic] Started new repair session.");

        BikeDTO bike = controller.enterBikeID("BIKE-001");
        if (bike != null) {
            System.out.println("[System]   Bike registered: ID=" + bike.getBikeID()
                    + ", Owner=" + bike.getOwnerName());
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

        RepairOrder repairOrder = controller.endRepair();
        printRepairOrder(repairOrder);
    }

    private void printRepairOrder(RepairOrder repairOrder) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("              REPAIR ORDER              ");
        System.out.println("========================================");
        System.out.println("Bike ID : " + repairOrder.getBike().getBikeID());
        System.out.println("Owner   : " + repairOrder.getBike().getOwnerName());
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
