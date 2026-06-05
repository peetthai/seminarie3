package model;

import java.util.List;

/**
 * Data Transfer Object that carries the details of a completed repair order
 * across layer boundaries, including from the model to the view and into the
 * repair order registry. Instances are immutable.
 */
public class RepairOrderDTO {
    private final String repairOrderID;
    private final BikeDTO bike;
    private final List<TaskDTO> tasks;
    private final String diagnosticReport;
    private final Amount total;

    /**
     * Creates a RepairOrderDTO with all details of the completed repair.
     *
     * @param repairOrderID    The unique identifier of the repair order.
     * @param bike             The bike that was repaired.
     * @param tasks            The list of repair tasks performed.
     * @param diagnosticReport The mechanic's diagnostic notes.
     * @param total            The total cost of all repair tasks.
     */
    public RepairOrderDTO(String repairOrderID, BikeDTO bike, List<TaskDTO> tasks,
                          String diagnosticReport, Amount total) {
        this.repairOrderID = repairOrderID;
        this.bike = bike;
        this.tasks = tasks;
        this.diagnosticReport = diagnosticReport;
        this.total = total;
    }

    /**
     * Returns the unique identifier of this repair order.
     *
     * @return The repair order ID string.
     */
    public String getRepairOrderID() {
        return repairOrderID;
    }

    /**
     * Returns the bike information for this repair order.
     *
     * @return The {@link BikeDTO} of the repaired bike.
     */
    public BikeDTO getBike() {
        return bike;
    }

    /**
     * Returns the list of repair tasks included in this order.
     *
     * @return An unmodifiable list of {@link TaskDTO} objects.
     */
    public List<TaskDTO> getTasks() {
        return tasks;
    }

    /**
     * Returns the mechanic's diagnostic report for this repair.
     *
     * @return The diagnostic report string.
     */
    public String getDiagnosticReport() {
        return diagnosticReport;
    }

    /**
     * Returns the total cost of all repair tasks in this order.
     *
     * @return The total as an {@link Amount}.
     */
    public Amount getTotal() {
        return total;
    }
}
