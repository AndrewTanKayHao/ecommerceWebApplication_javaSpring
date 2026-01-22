package ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Author: Angeline
@Controller
public class AdminController {

    @GetMapping({"/admin", "/admin/**"})
    public String forwardToReact() {
        return "forward:/index.html";
    }
}
