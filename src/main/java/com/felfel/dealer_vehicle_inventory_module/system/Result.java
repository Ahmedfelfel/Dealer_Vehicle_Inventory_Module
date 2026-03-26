package com.felfel.dealer_vehicle_inventory_module.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response wrapper for the application.
 * Contains status flags, HTTP-like status codes, messages, and the payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * Success indicator.
     */
    private Boolean flag;

    /**
     * Operation code (typically HTTP status code).
     */
    private Integer code;

    /**
     * Detailed status message.
     */
    private String message;

    /**
     * Response payload.
     */
    private Object data;

    /**
     * Constructs a result without a payload.
     *
     * @param flag    Success indicator.
     * @param code    Operation code.
     * @param message Status message.
     */
    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
