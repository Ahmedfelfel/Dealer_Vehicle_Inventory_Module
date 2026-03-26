package com.felfel.dealer_vehicle_inventory_module.tenant;

/**
 * Holds the current tenant identifier for the execution thread.
 * Uses {@link ThreadLocal} to ensure thread safety in a multi-threaded environment.
 */
public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /**
     * Sets the current tenant ID.
     *
     * @param tenantId the tenant id to set.
     */
    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    /**
     * Gets the current tenant ID.
     *
     * @return the current tenant id or null if not set.
     */
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    /**
     * Clears the current tenant context.
     * Should be called at the end of each request to prevent thread local leaks.
     */
    public static void clear() {
        currentTenant.remove();
    }
}
