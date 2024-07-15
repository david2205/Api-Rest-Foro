package com.alura.foro.api.modelo;

import com.alura.foro.api.dto.respuesta.DatosActualizarRespuesta;
import com.alura.foro.api.dto.respuesta.DatosRegistroRespuesta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Type topico;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private User autor;
    private Boolean solucion = false;

    public Answer(DatosRegistroRespuesta datos, Type topico, User autor) {
        this.mensaje = datos.mensaje();
        this.topico = topico;
        this.autor = autor;
        this.solucion = datos.solucion();
        if (datos.solucion()) {
            this.topico.setEstado(State.SOLUCIONADO);
        } else {
            this.topico.setEstado(State.NO_SOLUCIONADO);
        }
    }

    public void actualizarDatos(DatosActualizarRespuesta datosActualizar, Type topico, User autor) {
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
        if (topico != null) {
            this.topico = topico;
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (datosActualizar.solucion() != this.solucion) {
            this.solucion = datosActualizar.solucion();
        }
    }
}
