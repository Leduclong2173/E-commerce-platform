package com.duclong.ecommerce.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class DashboardClientController {

    @GetMapping("/client")
    public String getDashboardPage(Model model) {
        return "client/dashboard/showDashboard";
    }
}
