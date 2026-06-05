package integration;

/**
 * Creates and holds the single instance of each registry in the integration
 * layer, so that the same registries are shared by everyone that needs them.
 * This keeps registry creation in one place and keeps the constructors of the
 * classes that use the registries short.
 */
public class RegistryCreator {
    private final BikeRegistry bikeRegistry = new BikeRegistry();
    private final RepairTaskCatalog repairTaskCatalog = new RepairTaskCatalog();
    private final CustomerRegistry customerRegistry = new CustomerRegistry();
    private final RepairOrderRegistry repairOrderRegistry = new RepairOrderRegistry();

    /**
     * Returns the registry that stores bikes.
     *
     * @return The {@link BikeRegistry} instance.
     */
    public BikeRegistry getBikeRegistry() {
        return bikeRegistry;
    }

    /**
     * Returns the catalog that stores repair tasks.
     *
     * @return The {@link RepairTaskCatalog} instance.
     */
    public RepairTaskCatalog getRepairTaskCatalog() {
        return repairTaskCatalog;
    }

    /**
     * Returns the registry that stores customers.
     *
     * @return The {@link CustomerRegistry} instance.
     */
    public CustomerRegistry getCustomerRegistry() {
        return customerRegistry;
    }

    /**
     * Returns the registry that stores completed repair orders.
     *
     * @return The {@link RepairOrderRegistry} instance.
     */
    public RepairOrderRegistry getRepairOrderRegistry() {
        return repairOrderRegistry;
    }
}
