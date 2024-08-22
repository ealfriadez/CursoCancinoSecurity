package pe.edu.unfv.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("denyAll()")
public class HomeController {
	
	@GetMapping("/get")
	@PreAuthorize("hasAuthority('READ')")
	public String helloGet() {
		return "Hello World - GET";
	}
	
	@PostMapping("/pos")
	public String helloPost() {
		return "Hello World - POST";
	}
	
	@PutMapping("/put")
	public String helloPut() {
		return "Hello World - PUT";
	}
	
	@DeleteMapping("/delete")
	public String helloDelete() {
		return "Hello World - DELETE";
	}
	
	@PatchMapping("/patch")
	@PreAuthorize("hasAuthority('REFACTOR')")
	public String helloget() {
		return "Hello World - PATCH";
	}
}
