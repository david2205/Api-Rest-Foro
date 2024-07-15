package com.alura.foro.api.modelo;

import com.alura.foro.api.dto.topico.DatosActualizarTopico;
import com.alura.foro.api.dto.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name= "topicos")
@Entity(name="Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private State estado = State.NO_RESPONDIDO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private User autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Subject curso;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topico_id", referencedColumnName = "id")
    private List<Answer> respuestas = new ArrayList<>();

    public Type(DatosRegistroTopico datos, User autor, Subject curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = autor;
        this.curso = curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizar, User autor, Subject curso) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo();
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
        if (datosActualizar.estado() != datosActualizar.estado()) {
            this.estado = datosActualizar.estado();
        }
        if (autor != null) {
            this.autor = autor;
        }
        if (curso != null) {
            this.curso = curso;
        }
    }

    public void agregarRespuesta(Answer respuesta) {
        this.respuestas.add(respuesta);
        if (respuesta.getSolucion()) {
            this.estado = State.SOLUCIONADO;
        } else {
            this.estado = State.NO_SOLUCIONADO;
        }
    }

    public void cerrarTopico() {
        this.estado = State.CERRADO;
    }

    public void setEstado(State estado) {
        this.estado = estado;
    }
}