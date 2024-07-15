package com.alura.foro.api.dto.topico;
import com.alura.foro.api.modelo.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        State estado,
        @NotNull
        Long autorId,
        @NotNull
        Long cursoId) {
}
