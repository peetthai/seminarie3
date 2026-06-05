package controller;

import integration.BikeRegistry;
import integration.CustomerRegistry;
import integration.RegistryCreator;
import integration.RepairOrderRegistry;
import integration.RepairTaskCatalog;
import model.Amount;
import model.BikeDTO;
import model.CustomerDTO;
import model.RepairOrderDTO;
import model.RepairShop;
import model.TaskDTO;

import java.util.List;

/**
 * Coordinates use-case execution between the view and the model and integration layers.
 * All calls from the view pass through this class.
 */
public class Controller {
    private final RepairShop repairShop;
    private final BikeRegistry bikeRegistry;
    private final RepairTaskCatalog taskCatalog;
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;

    /**
     * Creates a Controller wired to the model and to the registries provided by
     * the given registry creator.
     *
     * @param repairShop      The repair shop facade (model layer).
     * @param registryCreator The creator that holds all integration-layer registries.
     */
    public Controller(RepairShop repairShop, RegistryCreator registryCreator) {
        this.repairShop = repairShop;
        this.bikeRegistry = registryCreator.getBikeRegistry();
        this.taskCatalog = registryCreator.getRepairTaskCatalog();
        this.customerRegistry = registryCreator.getCustomerRegistry();
        this.repairOrderRegistry = registryCreator.getRepairOrderRegistry();
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
     * Ends the current repair session, saves the resulting repair order in the
     * repair order registry, and returns it.
     *
     * @return The {@link RepairOrderDTO} summarising all repair details.
     */
    public RepairOrderDTO endRepair() {
        RepairOrderDTO repairOrder = repairShop.endRepair();
        repairOrderRegistry.saveRepairOrder(repairOrder);
        return repairOrder;
    }

    /**
     * Searches for a previously saved repair order with the given ID.
     *
     * @param repairOrderID The unique identifier of the repair order to find.
     * @return The matching {@link RepairOrderDTO}, or {@code null} if no repair
     *         order with the given ID has been saved.
     */
    public RepairOrderDTO findRepairOrder(String repairOrderID) {
        return repairOrderRegistry.findRepairOrderByID(repairOrderID);
    }

    /**
     * Searches for all saved repair orders belonging to the customer with the
     * given ID.
     *
     * @param customerID The unique identifier of the customer to search for.
     * @return A list of the customer's saved repair orders, possibly empty.
     */
    public List<RepairOrderDTO> findRepairOrdersForCustomer(String customerID) {
        return repairOrderRegistry.findRepairOrdersByCustomer(customerID);
    }

    /**
     * Searches for a customer with the given ID.
     *
     * @param customerID The unique identifier of the customer to find.
     * @return The matching {@link CustomerDTO}, or {@code null} if no customer
     *         with the given ID exists in the registry.
     */
    public CustomerDTO findCustomer(String customerID) {
        return customerRegistry.findCustomer(customerID);
    }
}
