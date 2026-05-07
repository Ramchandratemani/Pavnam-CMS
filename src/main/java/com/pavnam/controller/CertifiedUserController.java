package com.pavnam.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pavnam.model.CertifiedUser;
import com.pavnam.repository.CertifiedUserRepository;
import com.pavnam.service.CertifiedUserService;

@Controller
@RequestMapping("/certifiedUser")
public class CertifiedUserController {

    @Autowired
    private CertifiedUserRepository certifiedUserRepository;

    @Autowired
    private CertifiedUserService certifiedUserService;

    @GetMapping
    public String getCertifiedUsers(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Ensure user is logged in
        }

        String loggedInDealer = principal.getName(); // Fetch logged-in dealer's username
        List<CertifiedUser> certifiedUsers = certifiedUserRepository.findByEnrolledBy(loggedInDealer);
        
        model.addAttribute("certifiedUsers", certifiedUsers);
        return "dealer/certifiedUser"; // Ensure you have "certifiedUser.html" in templates
    }

        // View Certified User Details
    @GetMapping("/details/{id}")
    public String viewCertifiedUserDetails(@PathVariable Long id, Model model) {
        CertifiedUser user = certifiedUserService.getUserById(id);
        model.addAttribute("user", user);
        return "dealer/certifiedUserDetails"; // This refers to certifiedUserDetails.html
    }

    // Delete Certified User
    @GetMapping("/delete/{id}")
    public String deleteCertifiedUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        certifiedUserService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/certifiedUser";
    }

    @PutMapping("/{userId}/renew")
    public ResponseEntity<String> renewCertificate(@PathVariable Long userId) {
        certifiedUserService.renewCertifiedUser(userId);
        return ResponseEntity.ok("Certificate renewed successfully.");
    }


}
