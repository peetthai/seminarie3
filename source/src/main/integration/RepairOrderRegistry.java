package integration;

import model.RepairOrderDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores completed repair orders and provides lookup of saved orders.
 * In this implementation the orders are held in memory, simulating the database
 * that the integration layer would otherwise be responsible for calling.
 */
public class RepairOrderRegistry {
    private final Map<String, RepairOrderDTO> repairOrders = new LinkedHashMap<>();

    /**
     * Saves a completed repair order so that it can be retrieved later. An order
     * with an ID that is already stored overwrites the previously saved order.
     *
     * @param repairOrder The completed repair order to save.
     */
    public void saveRepairOrder(RepairOrderDTO repairOrder) {
        repairOrders.put(repairOrder.getRepairOrderID(), repairOrder);
    }

    /**
     * Searches for a saved repair order with the given ID.
     *
     * @param repairOrderID The unique identifier of the repair order to look up.
     * @return The matching {@link RepairOrderDTO}, or {@code null} if no repair
     *         order with the given ID has been saved.
     */
    public RepairOrderDTO findRepairOrderByID(String repairOrderID) {
        return repairOrders.get(repairOrderID);
    }

    /**
     * Searches for all saved repair orders belonging to the customer with the
     * given ID.
     *
     * @param customerID The unique identifier of the customer to search for.
     * @return A list of the matching repair orders, in the order they were saved.
     *         The list is empty if the customer has no saved repair orders.
     */
    public List<RepairOrderDTO> findRepairOrdersByCustomer(String customerID) {
        List<RepairOrderDTO> matches = new ArrayList<>();
        for (RepairOrderDTO order : repairOrders.values()) {
            if (order.getBike().getCustomer().getCustomerID().equals(customerID)) {
                matches.add(order);
            }
        }
        return matches;
    }
}
