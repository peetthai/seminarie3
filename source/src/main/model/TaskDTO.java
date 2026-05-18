package model;

/**
 * Data Transfer Object that carries repair task information across layer boundaries.
 * Instances are immutable.
 */
public class TaskDTO {
    private final String name;
    private final Amount cost;

    /**
     * Creates a TaskDTO with the specified task name and cost.
     *
     * @param name The name of the repair task.
     * @param cost The cost of the repair task.
     */
    public TaskDTO(String name, Amount cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Returns the repair task name.
     *
     * @return The task name string.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cost of this repair task.
     *
     * @return The cost as an {@link Amount}.
     */
    public Amount getCost() {
        return cost;
    }
}
