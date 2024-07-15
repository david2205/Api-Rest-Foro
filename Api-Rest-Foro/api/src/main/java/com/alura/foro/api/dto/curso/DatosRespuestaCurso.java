package com.alura.foro.api.dto.curso;

import com.alura.foro.api.modelo.Subject;

public record DatosRespuestaCurso(String nombre, String categoria) {

    public DatosRespuestaCurso(Subject curso) {
        this(curso.getNombre(), curso.getCategoria());
    }
}
