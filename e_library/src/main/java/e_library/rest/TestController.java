package e_library.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("TEST ENDPOINT CALLED!");
        return "Hello from Spring REST!";
    }
    
    @GetMapping("")
    public String index() {
        System.out.println("TEST ROOT CALLED!");
        return "Test controller is working!";
    }
}