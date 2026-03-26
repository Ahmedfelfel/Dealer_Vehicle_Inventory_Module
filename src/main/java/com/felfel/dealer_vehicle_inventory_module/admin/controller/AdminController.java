package com.felfel.dealer_vehicle_inventory_module.admin.controller;

import com.felfel.dealer_vehicle_inventory_module.admin.Dto.DealerCountBySubscriptionResponse;
import com.felfel.dealer_vehicle_inventory_module.admin.service.AdminService;
import com.felfel.dealer_vehicle_inventory_module.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Admin controller.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * Instantiates a new Admin controller.
     *
     * @param adminService the admin service
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Count dealers by subscription result.
     *
     * @return the result
     */
    @GetMapping("/dealers/countBySubscription")
    public Result countDealersBySubscription() {
        DealerCountBySubscriptionResponse data = adminService.countDealersBySubscription();
        return new Result(true, HttpStatus.OK.value(), "count success", data);
    }
}
