package com.alura.foro.api.dto.usuario;

import com.alura.foro.api.modelo.User;

public record DatosRespuestaUsuario(String nombre, String email, String tipo) {

    public DatosRespuestaUsuario(User usuario) {
        this(usuario.getNombre(), usuario.getEmail(), usuario.getTipo().toString());
    }
}
