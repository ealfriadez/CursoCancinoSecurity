package pe.edu.unfv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/liberada")
public class LiberadaController {

	@GetMapping("")
	public String home() {
		return "liberada/home";
	}
	
	@GetMapping("/liberada-2")
	public String restringido_2() {
		return "liberada/liberada";
	}
}
