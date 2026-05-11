package com.pavnam.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.pavnam.dto.ChangePasswordRequest;
import com.pavnam.dto.DealerRegistrationDTO;
import com.pavnam.model.CertifiedUser;
import com.pavnam.model.Dealers;
import com.pavnam.model.SuperAdmin;
import com.pavnam.model.Users;
import com.pavnam.repository.CertifiedUserRepository;
import com.pavnam.repository.DealerRepository;
import com.pavnam.repository.RoleRepository;
import com.pavnam.repository.SuperAdminRepository;
import com.pavnam.repository.UserRepository;
import com.pavnam.service.FileStorageService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private CertifiedUserRepository certifiedUserRepository;

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String appBaseUrl;

    // Helper method to create and save a User object
    private Users createAndSaveUser(String username, String email, String password, String roleName) {
        Users user = new Users();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder().encode(password)); // Encrypt password
        user.setRoles(roleRepository.findByName(roleName)); // Assign role
        uRepository.save(user); // Save user
        return user;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Login page
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Landing page
    }

    // @GetMapping("/cmsindex")
    // public String cmsIndex(Authentication authentication) {
    //         System.out.println(authentication.getAuthorities());
    //     return "cmsindex"; // CMS dashboard
    // }
    @GetMapping("/cmsindex")
    public String cmsIndex(Authentication authentication) {

        System.out.println(authentication);

        if(authentication != null) {
            System.out.println(authentication.getAuthorities());
        }

        return "cmsindex";
    }

    @GetMapping("/register")
    public String showCertifiedUserForm(Model model) {
        CertifiedUser certUser = new CertifiedUser();
        model.addAttribute("user", certUser);
        return "register";
    }

    @PostMapping("/add")
    public String saveCertifiedUser(@Valid @ModelAttribute("user") CertifiedUser certUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register"; // Show validation errors
        }

        String email = certUser.getEmail();
        if (email != null && !"".equals(email)) {
            CertifiedUser existingUser = certifiedUserRepository.findByEmail(email);
            if (existingUser != null) {
                bindingResult.rejectValue("email", "error.user", "Email is already taken");
                return "register";
            }
        }

        // Create and save the User object
        Users user = createAndSaveUser(certUser.getUsername(), certUser.getEmail(), certUser.getPassword(), "USERS");

        // Set the encrypted password and establish the relationship
        certUser.setPassword(user.getPassword());
        certUser.setRoles(roleRepository.findByName("USERS"));
        certUser.setUsers(List.of(user));
        certifiedUserRepository.save(certUser);

        return "redirect:/login";
    }

    @GetMapping("/register/admin")
    public String showDealerForm(Model model) {
        model.addAttribute("dealerForm", new DealerRegistrationDTO());
        return "registeradmin";
    }
    
    @PostMapping("/add/admin")
    public String saveDealer(@Valid @ModelAttribute("dealerForm") DealerRegistrationDTO dealerForm, 
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "registeradmin";
        }

        try {
            // Check for existing email
            if (dealerRepository.findByEmail(dealerForm.getEmail()) != null) {
                bindingResult.rejectValue("email", "error.dealer", "Email already exists");
                return "registeradmin";
            }

            // Create new Dealer entity
            Dealers dealer = new Dealers();
            dealer.setUsername(dealerForm.getUsername());
            dealer.setEmail(dealerForm.getEmail());
            dealer.setFullName(dealerForm.getFullName());
            dealer.setMobile(dealerForm.getMobile());
            dealer.setCity(dealerForm.getCity());
            dealer.setState(dealerForm.getState());
            dealer.setAddress(dealerForm.getAddress());
            dealer.setGovtId(dealerForm.getGovtId());
            dealer.setGovtIdNumber(dealerForm.getGovtIdNumber());
            dealer.setPassword(dealerForm.getPassword());
            // dealer.setRepassword(dealerForm.getRepassword());

            // Handle file uploads
            if (dealerForm.getProfilePicFile() != null && !dealerForm.getProfilePicFile().isEmpty()) {
                String profilePicPath = fileStorageService.storeFile(dealerForm.getProfilePicFile(), "profile-pics");
                dealer.setProfilePicPath(profilePicPath);
            }

            if (dealerForm.getGovtIdFile() != null && !dealerForm.getGovtIdFile().isEmpty()) {
                String govtIdPath = fileStorageService.storeFile(dealerForm.getGovtIdFile(), "govt-ids");
                dealer.setGovtIdFilePath(govtIdPath);
            }

            // Create and save the User object
            Users user = createAndSaveUser(dealer.getUsername(), dealer.getEmail(), dealer.getPassword(), "ADMIN");

            // Set relationships
            dealer.setPassword(user.getPassword());
            dealer.setRoles(roleRepository.findByName("ADMIN"));
            dealer.setUsers(List.of(user));
            
            dealerRepository.save(dealer);
            
            return "redirect:/login";
            
        } catch (IOException e) {
            model.addAttribute("error", "An error occurred while registering: " + e.getMessage());
            return "registeradmin";
        }
    }


    @GetMapping("/register/superadmin")
    public String showSuperAdminForm(Model model) {
        SuperAdmin superAdmin = new SuperAdmin();
        model.addAttribute("superAdmin", superAdmin);
        return "registerSuperAdmin";
    }

    @PostMapping("/add/superadmin")
    public String saveSuperAdmin(@Valid @ModelAttribute("superAdmin") SuperAdmin superAdmin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registerSuperAdmin"; // Show validation errors
        }

        String email = superAdmin.getEmail();
        if (email != null && !"".equals(email)) {
            SuperAdmin existingSuperAdmin = superAdminRepository.findByEmail(email);
            if (existingSuperAdmin != null) {
                bindingResult.rejectValue("email", "error.superadmin", "SuperAdmin with this email already exists");
                return "registerSuperAdmin";
            }
        }

        // Create and save the User object
        Users user = createAndSaveUser(superAdmin.getUsername(), superAdmin.getEmail(), superAdmin.getPassword(), "SUPERADMIN");

        // Set the encrypted password and establish the relationship
        superAdmin.setPassword(user.getPassword());
        superAdmin.setRoles(roleRepository.findByName("SUPERADMIN"));
        superAdmin.setUsers(List.of(user));
        superAdminRepository.save(superAdmin);

        return "redirect:/login";
    }

    @GetMapping("/api/dealer/me")
    public ResponseEntity<?> getLoggedInDealer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        String email = authentication.getName(); // Get email of logged-in user
        Dealers dealer = dealerRepository.findByEmail(email);
        
        if (dealer != null) {
            return ResponseEntity.ok(dealer.getId()); // Return only the dealer's ID
        } else {
            return ResponseEntity.status(404).body("Dealer not found");
        }
    }

        @GetMapping("/adminProfile")
        public String getDealerProfile(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Dealers dealer = dealerRepository.findByEmail(email);
        
            if (dealer != null) {
                model.addAttribute("dealer", dealer);
                return "dealer/adminProfile"; // Your profile view
            } else {
                return "redirect:/login"; // Redirect to login if not found
            }
        }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("email", new String()); // Placeholder for email input
        return "forgot-password"; // Return the view for the forgot password form
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        Users user = uRepository.findByEmail(email);
        if (user != null) {
            // Generate a unique token
            String token = UUID.randomUUID().toString();
            // Save the token in the database associated with the user
            user.setResetToken(token);
            uRepository.save(user); // Save the user with the token
    
            // Send the email with the reset link
            // String resetLink = "http://localhost:8080/reset-password?token=" + token;
            String resetLink = appBaseUrl + "/reset-password?token=" + token;
            sendResetEmail(email, resetLink);
    
            // Notify the user that the email has been sent
            model.addAttribute("message", "Password reset link has been sent to the registered email.");
        } else {
            // Notify the user that the email does not exist
            model.addAttribute("message", "No account was found with this email address, please retry.");
        }
        return "forgot-password"; // Return to the forgot password page
    }

    private void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetLink);
        mailSender.send(message);
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        Users user = uRepository.findByResetToken(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token.");
            return "login"; // Redirect to login if token is invalid
        }
        model.addAttribute("token", token);
        return "reset-password"; // Return the view for resetting the password
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        Users user = uRepository.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder().encode(newPassword)); // Encrypt the new password
            user.setResetToken(null); // Clear the reset token
            uRepository.save(user); // Save the updated user
            model.addAttribute("message", "Your password has been reset successfully.");
            return "login"; // Redirect to login page
        } else {
            model.addAttribute("error", "Invalid token.");
            return "reset-password"; // Return to reset password page with error
        }
    }
    
        
    @PostMapping("/api/dealers/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Get the email of the logged-in user
        Users user = uRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("{\"message\": \"User  not found\"}");
        }

        // Check if the current password is correct
        if (!passwordEncoder().matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body("{\"message\": \"Current password is incorrect\"}");
        }

        // Update the password
        user.setPassword(passwordEncoder().encode(request.getNewPassword()));
        uRepository.save(user); // Save the updated user

        return ResponseEntity.ok().body("{\"message\": \"Password changed successfully\"}");
    }

    @GetMapping("/profile")
    public String getSuperAdminProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Get the email of the logged-in user
        SuperAdmin superAdmin = superAdminRepository.findByEmail(email); // Fetch the Super Admin by email

        if (superAdmin != null) {
            model.addAttribute("superAdmin", superAdmin); // Add the Super Admin to the model
            return "superadmin/profile"; // Return the profile view
        } else {
            return "redirect:/login"; // Redirect to login if not found
        }
    }


    @GetMapping("/registerCertifiedUser")
    public String getMethodName(Model model) {
        CertifiedUser certUser = new CertifiedUser();
        model.addAttribute("user", certUser);
        return "registerCertifiedUser";
    }

@PostMapping("/addUser")
public String saveCertified(@ModelAttribute("user") CertifiedUser certUser, 
                            @RequestParam("govtIdFile") MultipartFile govtIdFile,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {  // Capture logged-in user
    if (bindingResult.hasErrors()) {
        return "registerCertifiedUser";
    }

    // Validate the file
    if (govtIdFile.isEmpty()) {
        model.addAttribute("fileError", "Government ID file is required");
        return "registerCertifiedUser";
    }

    try {
        // Check for existing email
        CertifiedUser existingUser = certifiedUserRepository.findByEmail(certUser.getEmail());
        if (existingUser != null) {
            model.addAttribute("emailError", "Email is already taken");
            return "registerCertifiedUser";
        }

        // Store the file
        String subdirectory = "government-ids/" + certUser.getUsername();
        String filePath = fileStorageService.storeFile(govtIdFile, subdirectory);
        certUser.setGovtIdFilePath(filePath);

        // Create base user
        Users user = new Users();
        user.setName(certUser.getFirstName() + " " + certUser.getLastName());
        user.setEmail(certUser.getEmail());
        user.setPassword(passwordEncoder().encode(certUser.getPassword()));
        user.setRoles(roleRepository.findByName("USERS"));

        // Save base user
        Users savedUser = uRepository.save(user);

        // Set up certified user
        certUser.setPassword(savedUser.getPassword());
        certUser.setRoles(roleRepository.findByName("USERS"));
        certUser.setUsers(List.of(savedUser));
        certUser.setRole("CertifiedUser");

        // Check if a dealer is registering the user
        if (principal != null) {
            String loggedInDealerEmail = principal.getName(); // Get dealer's email
            Dealers dealer = dealerRepository.findByEmail(loggedInDealerEmail);
            
            if (dealer != null) {
                certUser.setDealer(dealer);  // Store dealer ID
                certUser.setEnrolledBy(dealer.getEmail()); // Store dealer email
            }
        }

        // Save certified user
        certifiedUserRepository.save(certUser);

        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");
        return "redirect:/registerCertifiedUser";

    } catch (IOException e) {
        model.addAttribute("error", "An error occurred while saving the user: " + e.getMessage());
        model.addAttribute("user", certUser);
        return "registerCertifiedUser";
    }
}



}
