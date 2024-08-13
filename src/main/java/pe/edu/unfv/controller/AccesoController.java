package pe.edu.unfv.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.model.AutorizarModel;
import pe.edu.unfv.model.RolesModel;
import pe.edu.unfv.model.UsuariosModel;
import pe.edu.unfv.service.implement.AutorizarServiceImpl;
import pe.edu.unfv.service.implement.RolesServiceImpl;
import pe.edu.unfv.service.implement.UsuariosServiceImpl;
import pe.edu.unfv.util.Constantes;

@Controller
@RequestMapping("/acceso")
@AllArgsConstructor
public class AccesoController {	
	
	private RolesServiceImpl rolesServiceImpl;
	private UsuariosServiceImpl usuariosServiceImpl;
	private AutorizarServiceImpl autorizarServiceImpl;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			RedirectAttributes flash, Principal principal
			) {
		
		if(principal!=null) 
		{
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ya ha iniciado sesión anteriormente.");
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
			flash.addFlashAttribute("mensaje", "Ha cerrado sesión exitosamente.");
			return "redirect:/acceso/login";
		}
		return "acceso/login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		
		List<RolesModel> roles = this.rolesServiceImpl.getAllRol();
		
		model.addAttribute("roles", roles);
		model.addAttribute("usuario", new UsuariosModel());
		return "acceso/registro";
	}
	
	@PostMapping("/registro")
	public String registro_post(
			@Valid UsuariosModel usuario,
			@Valid AutorizarModel autorizacion,
			BindingResult result,
			RedirectAttributes flash,
			Model model,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "repeatPassword") String repeatPassword,
			@RequestParam(name = "rol_") String rol_){		
		
		if(!password.contentEquals(repeatPassword)) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Los datos ingresados no coinciden, por favor vuelva a intentarlo.");
			return "redirect:/acceso/registro";
		}
		
		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo "
								.concat(err.getField()
								.concat(" ")
								.concat(err.getDefaultMessage())));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			
			return "acceso/registro";
		}	
		
		//creamos el usuario
		UsuariosModel guardar = this.usuariosServiceImpl.saveUser(
				new UsuariosModel(
						usuario.getNombre(),
						usuario.getCorreo(),
						usuario.getTelefono(),
						this.bCryptPasswordEncoder.encode(usuario.getPassword()),
						Constantes.UNO,
						null));
		
		//creamos algun rol
		this.autorizarServiceImpl.saveAutorizar(
				new AutorizarModel(
						rol_, guardar));
		
		//redireccionamos	
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Te has registrado exitosamente.");
		return "redirect:/acceso/login";
	}
}
