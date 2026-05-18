package controller;

import integration.BikeRegistry;
import integration.RepairTaskCatalog;
import model.Amount;
import model.BikeDTO;
import model.RepairOrder;
import model.RepairShop;
import model.TaskDTO;

/**
 * Coordinates use-case execution between the view and the model and integration layers.
 * All calls from the view pass through this class.
 */
public class Controller {
    private final RepairShop repairShop;
    private final BikeRegistry bikeRegistry;
    private final RepairTaskCatalog taskCatalog;

    /**
     * Creates a Controller wired to the given dependencies.
     *
     * @param repairShop   The repair shop facade (model layer).
     * @param bikeRegistry The bike registry (integration layer).
     * @param taskCatalog  The repair task catalog (integration layer).
     */
    public Controller(RepairShop repairShop, BikeRegistry bikeRegistry,
                      RepairTaskCatalog taskCatalog) {
        this.repairShop = repairShop;
        this.bikeRegistry = bikeRegistry;
        this.taskCatalog = taskCatalog;
    }

    /**
     * Starts a new repair session.
     */
    public void startNewRepair() {
        repairShop.startRepair();
    }

    /**
     * Registers the bike with the given ID for the current repair session.
     *
     * @param bikeID The unique identifier of the bike to register.
     * @return The {@link BikeDTO} of the registered bike, or {@code null} if the
     *         bike ID does not exist in the registry.
     */
    public BikeDTO enterBikeID(String bikeID) {
        BikeDTO bike = bikeRegistry.findBike(bikeID);
        if (bike != null) {
            repairShop.registerBike(bike);
        }
        return bike;
    }

    /**
     * Adds the repair task with the given name to the current repair session.
     *
     * @param taskName The name of the repair task to add.
     * @return The updated running total as an {@link Amount}, or {@code null} if the
     *         task name does not exist in the catalog.
     */
    public Amount addRepairTask(String taskName) {
        TaskDTO task = taskCatalog.findTask(taskName);
        if (task != null) {
            return repairShop.addTask(task);
        }
        return null;
    }

    /**
     * Records the mechanic's diagnostic notes for the current repair session.
     *
     * @param report The diagnostic report text entered by the mechanic.
     */
    public void enterDiagnosticReport(String report) {
        repairShop.enterDiagnosticReport(report);
    }

    /**
     * Ends the current repair session and returns the completed repair order.
     *
     * @return The {@link RepairOrder} summarising all repair details.
     */
    public RepairOrder endRepair() {
        return repairShop.endRepair();
    }
}
