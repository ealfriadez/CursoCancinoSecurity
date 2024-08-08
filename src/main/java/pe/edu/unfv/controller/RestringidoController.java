package pe.edu.unfv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restringido")
public class RestringidoController {

	@GetMapping("")
	public String home() {
		return "restringido/home";
	}
	
	@GetMapping("/restringido-2")
	public String restringido_2() {
		return "restringido/restringido";
	}
}
