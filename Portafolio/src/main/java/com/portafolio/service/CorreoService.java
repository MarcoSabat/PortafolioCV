package com.portafolio.service;

public interface CorreoService {
    void enviarCorreo(
            String destinatario,
            String asunto, 
            String cuerpo);
}