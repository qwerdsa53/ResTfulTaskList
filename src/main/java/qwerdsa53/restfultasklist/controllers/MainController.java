package qwerdsa53.restfultasklist.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('USER')")
public class MainController {
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
