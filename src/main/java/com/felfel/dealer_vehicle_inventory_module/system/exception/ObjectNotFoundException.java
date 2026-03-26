package com.felfel.dealer_vehicle_inventory_module.system.exception;

/**
 * The type Object not found exception.
 */
public class ObjectNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Object not found exception.
     *
     * @param objectType the object type
     * @param Id         the id
     */
    public ObjectNotFoundException(String objectType, String Id) {
        super("Could not find "+objectType+" with ID " + Id + " :(");
    }

    /**
     * Instantiates a new Object not found exception.
     *
     * @param objectType the object type
     * @param Id         the id
     */
    public ObjectNotFoundException(String objectType, Integer Id) {
        super("Could not find "+objectType+" with ID " + Id + " :(");
    }

    /**
     * Instantiates a new Object not found exception.
     *
     * @param objectType the object type
     */
    public ObjectNotFoundException(String objectType)
    {
        super(("Could not find any "+objectType+" :("));
    }
}
