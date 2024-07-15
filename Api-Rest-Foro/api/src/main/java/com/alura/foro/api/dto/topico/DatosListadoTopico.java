package com.alura.foro.api.dto.topico;

import com.alura.foro.api.modelo.Type;

public record DatosListadoTopico(Long id, String titulo, String mensaje, String autor, String curso) {

    public DatosListadoTopico(Type topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}
