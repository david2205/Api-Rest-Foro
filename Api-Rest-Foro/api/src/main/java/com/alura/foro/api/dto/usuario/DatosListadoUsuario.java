package com.alura.foro.api.dto.usuario;

import com.alura.foro.api.modelo.User;

public record DatosListadoUsuario(Long id, String nombre, String email, String tipo) {

    public DatosListadoUsuario(User usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getTipo().toString());
    }
}
