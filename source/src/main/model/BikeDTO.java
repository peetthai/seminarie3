package model;

/**
 * Data Transfer Object that carries bike information across layer boundaries.
 * Instances are immutable.
 */
public class BikeDTO {
    private final String bikeID;
    private final String ownerName;

    /**
     * Creates a BikeDTO with the specified bike ID and owner name.
     *
     * @param bikeID    The unique identifier of the bike.
     * @param ownerName The full name of the bike's owner.
     */
    public BikeDTO(String bikeID, String ownerName) {
        this.bikeID = bikeID;
        this.ownerName = ownerName;
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
     * Returns the full name of the bike's owner.
     *
     * @return The owner's name.
     */
    public String getOwnerName() {
        return ownerName;
    }
}
