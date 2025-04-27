package com.portafolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Controller
public class PortafolioController {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    private boolean isAuthenticated = false;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
            @RequestParam String password,
            Model model) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            isAuthenticated = true;
            return "redirect:/";
        } else {
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        isAuthenticated = false;
        return "redirect:/login";
    }

    @GetMapping("/")
    public String inicio(Model model) {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        model.addAttribute("descripcion", "Hola, soy un estudiante de la universidad Fidélitas, apasionado por la programación y el diseño.");
        return "inicio";
    }

    @GetMapping("/experiencia")
    public String experiencia(Model model) {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        model.addAttribute("experiencia", "Aquí detallo mi experiencia laboral:");
        model.addAttribute("habilidades", "Aquí están mis principales habilidades.");
        return "experiencia";
    }

    @GetMapping("/contacto")
    public String contacto() {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        return "contacto";
    }

    @GetMapping("/curriculum")
    public String curriculum() {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        return "curriculum";
    }

    @GetMapping("/proyectos")
    public String proyectos() {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        return "proyectos";
    }

    @GetMapping("/curriculum/descargar")
    public ResponseEntity<Resource> descargarCurriculum() {
        if (!isAuthenticated) {
            return ResponseEntity.status(401).build(); // No autorizado
        }
        Resource file = new ClassPathResource("curriculum/CurriculumDePrueba.pdf");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"CurriculumDePrueba.pdf\"")
                .body(file);
    }

    @GetMapping("/proyectos/descargar/{nombre}")
    public ResponseEntity<Resource> descargarProyecto(@PathVariable String nombre) {
        if (!isAuthenticated) {
            return ResponseEntity.status(401).build(); // No autorizado
        }
        Resource file = new ClassPathResource("proyectos/" + nombre + ".zip");
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + ".zip\"")
                .body(file);
    }

    @PostMapping("/contacto")
    public String enviarCorreo(@RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam("mensaje") String mensaje,
            Model model) {
        if (!isAuthenticated) {
            return "redirect:/login";
        }
        model.addAttribute("mensajeEnviado", "Mensaje enviado exitosamente");
        return "contacto";
    }
}
