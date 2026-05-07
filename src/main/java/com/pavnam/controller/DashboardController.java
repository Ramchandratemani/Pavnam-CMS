package com.pavnam.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pavnam.model.CertifiedUser;
import com.pavnam.model.Dealers;
import com.pavnam.service.CertifiedUserService;
import com.pavnam.service.DashboardService;
import com.pavnam.service.DealerService;


@Controller
public class DashboardController {

    @Autowired
    private CertifiedUserService certifiedUserService;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private DashboardService dashboardService;

    public DashboardController(CertifiedUserService certifiedUserService) {
        this.certifiedUserService = certifiedUserService;
    }


    @GetMapping("/admin-dashboard")
    public String dashboard(Model model, Principal principal) {
        // Fetch logged-in dealer's email
        String dealerEmail = principal.getName(); // Get logged-in user's email
        Dealers dealer = dealerService.getDealerByEmail(dealerEmail);

        if (dealer != null) {
            // Get total certified users enrolled by this dealer
            int totalCertifiedUsers = certifiedUserService.getCertifiedUserCountByDealerId(dealer.getId());

            // Get total certified users for current month
            int currentYear = LocalDate.now().getYear();
            int currentMonth = LocalDate.now().getMonthValue();
            long certifiedUsersThisMonth = dashboardService.getCertifiedUsersByMonth(currentYear, currentMonth);

            // Pass data to template
            model.addAttribute("dealerName", dealer.getFullName());
            model.addAttribute("totalCertifiedUsers", totalCertifiedUsers);
            model.addAttribute("certifiedUsersThisMonth", certifiedUsersThisMonth);
        } else {
            // If dealer not found, set default values
            model.addAttribute("dealerName", "Dealer Not Found");
            model.addAttribute("totalCertifiedUsers", "No Data");
            model.addAttribute("certifiedUsersThisMonth", "No Data");
        }

        List<CertifiedUser> expiringUsers = certifiedUserService.getExpiringCertificates();
        model.addAttribute("expiringUsers", expiringUsers);


        // model.addAttribute("totalCertifiedUsers", dashboardService.getTotalCertifiedUsers());
        // model.addAttribute("certifiedUsersThisMonth", certifiedUsersThisMonth);

        return "dealer/admin-dashboard";
    }

}