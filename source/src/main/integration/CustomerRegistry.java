package integration;

import model.CustomerDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles retrieval of customer information from persistent storage.
 * In this implementation, customers are stored in memory as sample data.
 */
public class CustomerRegistry {
    private final Map<String, CustomerDTO> customers = new HashMap<>();

    /**
     * Creates a CustomerRegistry pre-loaded with sample customer records.
     */
    public CustomerRegistry() {
        customers.put("CUST-001", new CustomerDTO("CUST-001", "Alice Svensson", "070-111 11 11"));
        customers.put("CUST-002", new CustomerDTO("CUST-002", "Bob Lindqvist", "070-222 22 22"));
        customers.put("CUST-003", new CustomerDTO("CUST-003", "Carl Johansson", "070-333 33 33"));
    }

    /**
     * Looks up and returns the customer with the given ID.
     *
     * @param customerID The unique identifier of the customer to look up.
     * @return The {@link CustomerDTO} for the found customer, or {@code null} if no
     *         customer with the given ID exists in the registry.
     */
    public CustomerDTO findCustomer(String customerID) {
        return customers.get(customerID);
    }
}
