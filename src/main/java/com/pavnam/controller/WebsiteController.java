package com.pavnam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

    @GetMapping("/about") 
    public String about() {
        return "website/about";
    }

    @GetMapping("/certification") 
    public String certification() {
        return "website/certification";
    }

    @GetMapping("/contact") 
    public String contact() {
        return "website/contact";
    }

    @GetMapping("/faq") 
    public String faq() {
        return "website/faq";
    }

    @GetMapping("/pavnamcms") 
    public String pavnamcms() {
        return "website/pavnamcms";
    }

    @GetMapping("/supporting") 
    public String supporting() {
        return "website/supporting";
    }

    @GetMapping("/vegrevolution") 
    public String vegrevolution() {
        return "website/vegrevolution";
    }

    @GetMapping("/superadmin-dashboard") 
    public String superadmindashboard() {
        return "superadmin/superadmin-dashboard";
    }

    // @GetMapping("/admin-dashboard") 
    // public String admindashboard() {
    //     return "dealer/admin-dashboard";
    // }
    
    
}
