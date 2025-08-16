package com.Kali.billingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    private List<OrderResponse>recentOrders;
    private Double todaySales;
    private Long todayOrderCount;
    public DashboardResponse(Double todaySales, Long todayOrderCount, List<OrderResponse> recentOrders) {
        this.todaySales = todaySales;
        this.todayOrderCount = todayOrderCount;
        this.recentOrders = recentOrders;
    }
}
