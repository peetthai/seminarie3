package model;

/**
 * Facade for the model layer. Manages the lifecycle of one repair session at a time
 * and delegates all operations to the current {@link ActiveRepair}.
 */
public class RepairShop {

    private ActiveRepair currentRepair;
    private int nextRepairOrderNumber = 1;

    /**
     * Creates a new RepairShop ready to accept repair sessions.
     */
    public RepairShop() {
    }

    /**
     * Starts a new repair session, discarding any previously active session.
     */
    public void startRepair() {
        currentRepair = new ActiveRepair();
    }

    /**
     * Registers the bike that will be worked on during the current repair session.
     *
     * @param bike The {@link BikeDTO} of the bike to register.
     */
    public void registerBike(BikeDTO bike) {
        currentRepair.registerBike(bike);
    }

    /**
     * Adds a repair task to the current session and returns the updated running total.
     *
     * @param task The {@link TaskDTO} of the task to add.
     * @return The running total after the task has been added.
     */
    public Amount addTask(TaskDTO task) {
        return currentRepair.addTask(task);
    }

    /**
     * Records the mechanic's diagnostic notes for the current repair session.
     *
     * @param report The diagnostic report text entered by the mechanic.
     */
    public void enterDiagnosticReport(String report) {
        currentRepair.enterDiagnosticReport(report);
    }

    /**
     * Ends the current repair session and returns the completed repair order,
     * assigning it a unique repair order identifier.
     *
     * @return The {@link RepairOrderDTO} for the finished repair.
     */
    public RepairOrderDTO endRepair() {
        String repairOrderID = "RO-" + nextRepairOrderNumber++;
        return currentRepair.endRepair(repairOrderID);
    }
}
