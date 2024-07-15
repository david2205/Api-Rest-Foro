package com.alura.foro.api.dto.topico;

import com.alura.foro.api.modelo.Type;

public record DatosRespuestaTopico(String titulo, String mensaje, String autor, String curso) {

    public DatosRespuestaTopico(Type topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getAutor().getNombre(), topico.getCurso().getNombre());
    }
}
