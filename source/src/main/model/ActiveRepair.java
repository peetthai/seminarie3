package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents one ongoing repair session for a single bike.
 * Only accessible within the model package via {@link RepairShop}.
 */
class ActiveRepair {
    private BikeDTO bike;
    private final List<TaskDTO> tasks = new ArrayList<>();
    private String diagnosticReport = "";
    private Amount total = new Amount(0);

    /**
     * Registers the bike that is being repaired in this session.
     *
     * @param bike The bike to register.
     */
    void registerBike(BikeDTO bike) {
        this.bike = bike;
    }

    /**
     * Adds a repair task to this session and returns the updated running total.
     *
     * @param task The repair task to add.
     * @return The new running total after adding the task.
     */
    Amount addTask(TaskDTO task) {
        tasks.add(task);
        total = calculateTotal();
        return total;
    }

    /**
     * Records the mechanic's diagnostic notes for this repair session.
     *
     * @param report The diagnostic report text entered by the mechanic.
     */
    void enterDiagnosticReport(String report) {
        this.diagnosticReport = report;
    }

    /**
     * Ends the repair session and returns a completed repair order.
     *
     * @return The {@link RepairOrder} summarising all repair details.
     */
    RepairOrder endRepair() {
        List<TaskDTO> immutableTasks = Collections.unmodifiableList(new ArrayList<>(tasks));
        return new RepairOrder(bike, immutableTasks, diagnosticReport, total);
    }

    private Amount calculateTotal() {
        Amount sum = new Amount(0);
        for (TaskDTO task : tasks) {
            sum = sum.add(task.getCost());
        }
        return sum;
    }
}
