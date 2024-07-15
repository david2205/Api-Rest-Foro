package com.alura.foro.api.dto.respuesta;

import com.alura.foro.api.modelo.Answer;

public record DatosListadoRespuesta(Long id, String mensaje, String topico, String autor) {

    public DatosListadoRespuesta(Answer respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre());
    }
}
