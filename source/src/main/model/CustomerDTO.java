package model;

/**
 * Data Transfer Object that carries customer information across layer boundaries.
 * Instances are immutable.
 */
public class CustomerDTO {
    private final String customerID;
    private final String name;
    private final String phoneNumber;

    /**
     * Creates a CustomerDTO with the specified ID, name and phone number.
     *
     * @param customerID  The unique identifier of the customer.
     * @param name        The full name of the customer.
     * @param phoneNumber The customer's contact phone number.
     */
    public CustomerDTO(String customerID, String name, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the customer's unique identifier.
     *
     * @return The customer ID string.
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Returns the full name of the customer.
     *
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer's contact phone number.
     *
     * @return The phone number string.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
