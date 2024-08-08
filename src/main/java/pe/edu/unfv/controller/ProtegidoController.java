package pe.edu.unfv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protegido")
public class ProtegidoController {

	@GetMapping("")
	public String home() {
		return "protegido/home";
	}
	
	@GetMapping("/protegido-2")
	public String protegido_2() {
		return "protegido/protegido";
	}
}
