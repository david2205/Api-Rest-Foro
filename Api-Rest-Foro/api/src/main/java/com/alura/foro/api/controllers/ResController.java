package com.alura.foro.api.controllers;

import com.alura.foro.api.dto.respuesta.*;
import com.alura.foro.api.modelo.State;
import com.alura.foro.api.modelo.Answer;
import com.alura.foro.api.modelo.Type;
import com.alura.foro.api.modelo.User;
import com.alura.foro.api.repository.ResRepository;
import com.alura.foro.api.repository.TopRepository;
import com.alura.foro.api.repository.UsuRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class ResController {

    private final ResRepository resRepository;
    private final TopRepository topRepository;
    private final UsuRepository usuRepository;

    public ResController(ResRepository resRepository, TopRepository topRepository, UsuRepository usuRepository) {
        this.resRepository = resRepository;
        this.topRepository = topRepository;
        this.usuRepository = usuRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRetornoRespuesta> registrar(@RequestBody DatosRegistroRespuesta datosRegistro, UriComponentsBuilder uri) {
        Type topico = topRepository.getReferenceById(datosRegistro.topicoId());
        if (topico.getEstado() == State.CERRADO) {
           return ResponseEntity.unprocessableEntity().build();
        }

        User autor = usuRepository.getReferenceById(datosRegistro.autorId());
        Answer respuesta = resRepository.save(new Answer(datosRegistro, topico, autor));
        topico.agregarRespuesta(respuesta);
        DatosRetornoRespuesta datosRespuesta = new DatosRetornoRespuesta(respuesta);
        URI url = uri.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(resRepository.findAll(paginacion).map(DatosListadoRespuesta::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRetornoRespuestaId> retornaDatos(@PathVariable Long id) {
        Answer respuesta = resRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRetornoRespuestaId(respuesta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> actualizar(@RequestBody DatosActualizarRespuesta datosActualizar) {
        Answer respuesta = resRepository.getReferenceById(datosActualizar.id());
        Type topico = topRepository.getReferenceById(datosActualizar.topicoId());
        User autor = usuRepository.getReferenceById(datosActualizar.autorId());
        respuesta.actualizarDatos(datosActualizar, topico, autor);
        return ResponseEntity.ok( new DatosRetornoRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Answer respuesta = resRepository.getReferenceById(id);
        resRepository.delete(respuesta);
        return ResponseEntity.noContent().build();
    }
}
