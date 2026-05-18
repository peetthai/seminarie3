package integration;

import model.Amount;
import model.TaskDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles retrieval of repair task information from persistent storage.
 * In this implementation, tasks are stored in memory as sample data.
 */
public class RepairTaskCatalog {
    private final Map<String, TaskDTO> tasks = new HashMap<>();

    /**
     * Creates a RepairTaskCatalog pre-loaded with sample repair task records.
     */
    public RepairTaskCatalog() {
        tasks.put("Brake Pad Replacement",
                new TaskDTO("Brake Pad Replacement", new Amount(350.00)));
        tasks.put("Tire Replacement",
                new TaskDTO("Tire Replacement", new Amount(500.00)));
        tasks.put("Battery Check",
                new TaskDTO("Battery Check", new Amount(200.00)));
        tasks.put("Chain Lubrication",
                new TaskDTO("Chain Lubrication", new Amount(150.00)));
    }

    /**
     * Looks up and returns the repair task with the given name.
     *
     * @param taskName The name of the repair task to look up.
     * @return The {@link TaskDTO} for the found task, or {@code null} if no task
     *         with the given name exists in the catalog.
     */
    public TaskDTO findTask(String taskName) {
        return tasks.get(taskName);
    }
}
