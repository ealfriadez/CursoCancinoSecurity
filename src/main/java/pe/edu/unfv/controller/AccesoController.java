package pe.edu.unfv.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/acceso")
public class AccesoController {

	/*
	 * @GetMapping("/login") public String login_(
	 * 
	 * @RequestParam(value = "error", required = false) String error,
	 * 
	 * @RequestParam(value = "logout", required = false) String logout,
	 * RedirectAttributes flash, Principal principal){
	 * 
	 * if (principal != null) { flash.addFlashAttribute("clase", "success");
	 * flash.addFlashAttribute("mensaje", "Ya ha iniciado sesion anteriormente");
	 * return "redirect:/"; } if (error != null) { flash.addFlashAttribute("clase",
	 * "danger"); flash.addFlashAttribute("mensaje",
	 * "Los datos ingresados no son correctos, por favor vuelva a intentarlo.");
	 * return "redirect:/acceso/login"; } if (logout != null) {
	 * flash.addFlashAttribute("clase", "success");
	 * flash.addFlashAttribute("mensaje", "Ha cerrado la sesion exitosamente.");
	 * return "redirect:/acceso/login"; }
	 * 
	 * return "acceso/login"; }
	 */
	
	@GetMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value="logout", required=false) String logout , 
			RedirectAttributes flash, 
			Principal principal )
	{
		if(principal!=null) 
		{
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ya ha iniciado sesión anteriormente ");
			return "redirect:/";
		}
		if(error!=null) 
		{
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Los datos ingresados no son correctos, por favor vuelva a intentarlo.");
			return "redirect:/acceso/login";
		}
		if(logout!=null) 
		{
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ha cerrado sesión exitosamente");
			return "redirect:/acceso/login";
		}
		return "acceso/login";
	}
}
