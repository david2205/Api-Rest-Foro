package com.alura.foro.api.dto.usuario;

import com.alura.foro.api.modelo.User;

public record DatosRespuestaUsuarioId(String nombre, String email, String contrasena, String tipo) {

    public DatosRespuestaUsuarioId(User usuario) {
        this(usuario.getNombre(), usuario.getEmail(), usuario.getContrasena(), usuario.getTipo().toString());
    }
}
