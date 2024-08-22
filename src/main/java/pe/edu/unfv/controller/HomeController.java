package pe.edu.unfv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
	
	@GetMapping("demo")
	public String home_home() {
		return "ok - vamos bien";
	}
}
