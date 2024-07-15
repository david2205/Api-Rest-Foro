package com.alura.foro.api.dto.respuesta;

import com.alura.foro.api.dto.topico.DatosRespuestaTopico;
import com.alura.foro.api.dto.usuario.DatosRespuestaUsuario;
import com.alura.foro.api.modelo.Answer;

public record DatosRetornoRespuestaId(String mensaje, DatosRespuestaTopico topico, String fechaCreacion, DatosRespuestaUsuario autor, String solucion) {

    public DatosRetornoRespuestaId(Answer respuesta) {
        this(respuesta.getMensaje(), new DatosRespuestaTopico(respuesta.getTopico()), respuesta.getFechaCreacion().toString(),
                new DatosRespuestaUsuario(respuesta.getAutor()), respuesta.getSolucion().toString());
    }
}
