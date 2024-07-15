package com.alura.foro.api.controllers;

import com.alura.foro.api.dto.usuario.*;
import com.alura.foro.api.modelo.User;
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
@RequestMapping("/usuarios")
public class UsuController {

    private final UsuRepository usuarioRepository;

    public UsuController(UsuRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrar(@RequestBody DatosRegistroUsuario datosRegistro, UriComponentsBuilder uri) {
        User usuario = usuarioRepository.save(new User(datosRegistro));
        DatosRespuestaUsuario datosRespuesta = new DatosRespuestaUsuario(usuario);
        URI url = uri.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuarioId> retornaDatos(@PathVariable Long id) {
        User usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaUsuarioId(usuario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizar(@RequestBody DatosActualizarUsuario datosActualizar) {
        User usuario = usuarioRepository.getReferenceById(datosActualizar.id());
        usuario.actualizarDatos(datosActualizar);
        return ResponseEntity.ok( new DatosRespuestaUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        User usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
