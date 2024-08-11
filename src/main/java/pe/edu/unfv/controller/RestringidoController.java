package pe.edu.unfv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.unfv.security.service.PermisosServices;

@Controller
@RequestMapping("/restringido")
@AllArgsConstructor
@Slf4j
public class RestringidoController {

	private PermisosServices permisosServices;
	
	@GetMapping("")
	public String home(RedirectAttributes flash) {
		if (this.permisosServices.verifyRol("ROLE_ADMIN")) {
			log.info(this.permisosServices.toString());
			return "restringido/home";
		}else {
			flash.addFlashAttribute("clase", "warning");
			flash.addFlashAttribute("mensaje", "No tienes acceso a este contenido.!");
			return "redirect:/";
		}		
	}
	
	@GetMapping("/restringido-2")
	public String restringido_2() {
		return "restringido/restringido";
	}
}
