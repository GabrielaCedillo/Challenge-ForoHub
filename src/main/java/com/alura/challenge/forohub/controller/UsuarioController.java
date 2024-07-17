package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.domain.usuarios.DatosListadoUsuario;
import com.alura.challenge.forohub.domain.usuarios.DatosRegistroUsuario;
import com.alura.challenge.forohub.domain.usuarios.IUsuarioRepository;
import com.alura.challenge.forohub.domain.usuarios.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosListadoUsuario> agregarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(datosRegistroUsuario);
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosListadoUsuario datosListadoUsuarios = new DatosListadoUsuario(
                usuario.getId(),
                usuario.getNombre()
        );
        URI url = uriComponentsBuilder.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosListadoUsuarios);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> mostrarUsuarios(@PageableDefault(size = 10) Pageable pageable) {
        Page<DatosListadoUsuario> listadoUsuarios = usuarioRepository.findAll(pageable).map(DatosListadoUsuario::new);
        return ResponseEntity.ok(listadoUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuario> muestraUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        DatosListadoUsuario datosListadoUsuarios = new DatosListadoUsuario(
                usuario.getId(),
                usuario.getNombre()
        );
        /*List<String> lista = usuario.getPerfiles().stream().map(Perfil::getNombre).toList();
        System.out.println(lista);  // prueba de código arbitraria para extraer el ó los perfiles desde el usuario  */
        return ResponseEntity.ok(datosListadoUsuarios);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoUsuario> actualizaUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosListadoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
