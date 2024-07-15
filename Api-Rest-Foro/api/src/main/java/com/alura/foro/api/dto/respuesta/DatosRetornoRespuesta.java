package com.alura.foro.api.dto.respuesta;

import com.alura.foro.api.modelo.Answer;

public record DatosRetornoRespuesta(String mensaje, String topico, String autor) {

    public DatosRetornoRespuesta(Answer respuesta) {
        this(respuesta.getMensaje(), respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre());
    }
}
