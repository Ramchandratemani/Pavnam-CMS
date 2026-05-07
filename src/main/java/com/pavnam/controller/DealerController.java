package com.pavnam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pavnam.model.Dealers;
import com.pavnam.service.DealerService;
import com.pavnam.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private DealerService dealerService;



    @Autowired
    private FileStorageService fileStorageService;

    // Fetch Dealer Details
    @GetMapping("/{id}")
    public ResponseEntity<Dealers> getDealerById(@PathVariable("id") Long id) {
        Dealers dealer = dealerService.getDealerById(id);
        return ResponseEntity.ok(dealer);
    }


    @PostMapping("/{id}")
    public ModelAndView updateDealer(
            @PathVariable("id") Long id,
            @RequestParam(required = false) MultipartFile profilepic,
            @RequestParam(required = false) MultipartFile govtIdFile,
            @RequestParam String fullName,
            @RequestParam String mobile,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String address,
            @RequestParam String govtId,
            @RequestParam String govtIdNumber,
            HttpServletRequest request) throws IOException {

        // Get existing dealer
        Dealers existingDealer = dealerService.getDealerById(id);

        // Update basic fields
        existingDealer.setFullName(fullName);
        existingDealer.setMobile(mobile);
        existingDealer.setCity(city);
        existingDealer.setState(state);
        existingDealer.setAddress(address);
        existingDealer.setGovtId(govtId);
        existingDealer.setGovtIdNumber(govtIdNumber);

        // Handle profile picture upload
        if (profilepic != null && !profilepic.isEmpty()) {
            // Delete old file if exists
            if (existingDealer.getProfilePicPath() != null) {
                fileStorageService.deleteFile(existingDealer.getProfilePicPath());
            }
            String profilePicPath = fileStorageService.storeFile(profilepic, "profile-pics");
            existingDealer.setProfilePicPath(profilePicPath);
        }

        // Handle government ID file upload
        if (govtIdFile != null && !govtIdFile.isEmpty()) {
            // Delete old file if exists
            if (existingDealer.getGovtIdFilePath() != null) {
                fileStorageService.deleteFile(existingDealer.getGovtIdFilePath());
            }
            String govtIdPath = fileStorageService.storeFile(govtIdFile, "govt-ids");
            existingDealer.setGovtIdFilePath(govtIdPath);
        }

        

        // Save updated dealer using the service
        dealerService.saveDealer(existingDealer);
        
        // Create a ModelAndView to redirect to the same page
        ModelAndView modelAndView = new ModelAndView("redirect:" + request.getHeader("referer"));
        modelAndView.addObject("message", "Dealer updated successfully!");
        return modelAndView;
    }

    
    @GetMapping("/{id}/profilepic")
    public ResponseEntity<Resource> getProfilePic(@PathVariable("id") Long id) {
        try {
            Dealers dealer = dealerService.getDealerById(id);
            if (dealer.getProfilePicPath() != null) {
                Path filePath = Paths.get(uploadDir).resolve(dealer.getProfilePicPath());
                Resource resource = new UrlResource(filePath.toUri());

                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/govtIdFile")
    public ResponseEntity<Resource> getGovtIdFile(@PathVariable("id") Long id) {
        try {
            Dealers dealer = dealerService.getDealerById(id);
            if (dealer.getGovtIdFilePath() != null) {
                Path filePath = Paths.get(uploadDir).resolve(dealer.getGovtIdFilePath());
                Resource resource = new UrlResource(filePath.toUri());

                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dealer/edit/{id}")
    public String editDealer(@PathVariable Long id, Model model) {
        Dealers dealer = dealerService.getDealerById(id);
        model.addAttribute("dealer", dealer);
        return "dealer/edit"; // Return the name of your Thymeleaf template
    }

    @GetMapping("/adminProfile")
    public String adminProfile() {
        return "dealer/adminProfile";
    }

    @GetMapping("/registeradmin")
    public String registeradmin() {
        return "/registeradmin";
    }
    
    
}