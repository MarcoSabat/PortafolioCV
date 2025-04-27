package com.portafolio.controller;

import com.portafolio.service.CorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    @PostMapping("/correo/enviar")
    public String enviarMensaje(@RequestParam String nombre,
            @RequestParam String correoElectronico,
            @RequestParam String mensaje, Model model) {
        String destinatario = "marcosabatgamboa@gmail.com";
        String asunto = "Nuevo mensaje de correo: " + nombre;
        String cuerpo = "Nombre: " + nombre + "\n"
                + "Correo Electrónico: " + correoElectronico + "\n"
                + "Mensaje: " + mensaje;

        correoService.enviarCorreo(destinatario, asunto, cuerpo);

        // Añadir mensaje de éxito al modelo para mostrarlo en la vista
        model.addAttribute("successMessage", "¡Mensaje enviado correctamente!");

        return "contacto"; // Regresar a la misma página (contacto.html)
    }
}
