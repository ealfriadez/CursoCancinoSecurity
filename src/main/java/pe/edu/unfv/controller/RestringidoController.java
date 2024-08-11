package pe.edu.unfv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/restringido")
//@Slf4j
public class RestringidoController {
	
	@GetMapping("")
	public String home(RedirectAttributes flash) {	
		return "restringido/home";		
	}
	
	@GetMapping("/restringido-2")
	public String restringido_2() {
		return "restringido/restringido";
	}
}
