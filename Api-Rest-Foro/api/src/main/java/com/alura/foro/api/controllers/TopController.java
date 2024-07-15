package com.alura.foro.api.controllers;

import com.alura.foro.api.dto.topico.*;
import com.alura.foro.api.modelo.Subject;
import com.alura.foro.api.modelo.Type;
import com.alura.foro.api.modelo.User;
import com.alura.foro.api.repository.CurRepository;
import com.alura.foro.api.repository.TopRepository;
import com.alura.foro.api.repository.UsuRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopController {

    private final TopRepository topRepository;
    private final UsuRepository usuRepository;
    private final CurRepository curRepository;

    public TopController(TopRepository topRepository, UsuRepository usuRepository, CurRepository curRepository) {
        this.topRepository = topRepository;
        this.usuRepository = usuRepository;
        this.curRepository = curRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrar(@RequestBody @Valid DatosRegistroTopico datosRegistro, UriComponentsBuilder uri) {
        User autor = usuRepository.getReferenceById(datosRegistro.autorId());
        Subject curso = curRepository.getReferenceById(datosRegistro.cursoId());
        Type topico = topRepository.save(new Type(datosRegistro, autor, curso));
        DatosRespuestaTopico datosRespuesta = new DatosRespuestaTopico(topico);
        URI url = uri.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listar(@PageableDefault(size = 10)Pageable paginacion) {
        return ResponseEntity.ok(topRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopicoId> retornaDatos(@PathVariable Long id) {
        Type topico = topRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaTopicoId(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizar(@RequestBody @Valid DatosActualizarTopico datosActualizar) {
        User autor = usuRepository.getReferenceById(datosActualizar.autorId());
        Subject curso = curRepository.getReferenceById(datosActualizar.cursoId());
        Type topico = topRepository.getReferenceById(datosActualizar.id());
        topico.actualizarDatos(datosActualizar, autor, curso);
        return ResponseEntity.ok( new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Type topico = topRepository.getReferenceById(id);
        //topicoRepository.delete(topico);
        topico.cerrarTopico();
        return ResponseEntity.noContent().build();
    }
}
