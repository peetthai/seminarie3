package model;

/**
 * Data Transfer Object that carries bike information across layer boundaries.
 * Each bike is associated with the customer who owns it. Instances are immutable.
 */
public class BikeDTO {
    private final String bikeID;
    private final CustomerDTO customer;

    /**
     * Creates a BikeDTO with the specified bike ID and owning customer.
     *
     * @param bikeID   The unique identifier of the bike.
     * @param customer The customer who owns the bike.
     */
    public BikeDTO(String bikeID, CustomerDTO customer) {
        this.bikeID = bikeID;
        this.customer = customer;
    }

    /**
     * Returns the bike's unique identifier.
     *
     * @return The bike ID string.
     */
    public String getBikeID() {
        return bikeID;
    }

    /**
     * Returns the customer who owns this bike.
     *
     * @return The owning {@link CustomerDTO}.
     */
    public CustomerDTO getCustomer() {
        return customer;
    }
}
