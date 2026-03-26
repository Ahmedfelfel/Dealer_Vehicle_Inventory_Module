package com.felfel.dealer_vehicle_inventory_module.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * The type Tenant context filter.
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           var tenantId = request.getHeader("X-Tenant-Id");
           if (tenantId != null && !tenantId.isBlank()) {
               TenantContext.setCurrentTenant(tenantId);
               filterChain.doFilter(request, response);
           } else {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing X-Tenant-ID");
           }
       }
       finally {
           TenantContext.clear();
       }

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return  path.startsWith("/h2-console")||path.startsWith("/admin/dealers/countBySubscription");
    }
}
