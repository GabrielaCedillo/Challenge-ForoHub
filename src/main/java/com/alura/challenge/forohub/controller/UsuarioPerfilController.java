package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.domain.usuarioPerfil.DatosListadoUsuarioPerfil;
import com.alura.challenge.forohub.domain.usuarioPerfil.DatosRegistroUsuarioPerfil;
import com.alura.challenge.forohub.domain.usuarioPerfil.UsuarioPerfilService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/usuarioperfil")
@SecurityRequirement(name = "bearer-key")
public class UsuarioPerfilController {
    @Autowired
    private UsuarioPerfilService usuarioPerfilService;

    @PostMapping
    public ResponseEntity<DatosListadoUsuarioPerfil> agregarUsuariosPerfiles(@RequestBody @Valid DatosRegistroUsuarioPerfil datosRegistroUsuarioPerfil) {
        return ResponseEntity.ok(usuarioPerfilService.agregarPefil(datosRegistroUsuarioPerfil));
    }

    @GetMapping
    public ResponseEntity<List<DatosListadoUsuarioPerfil>> mostrarPerfilesUsuarios() {
        return ResponseEntity.ok(usuarioPerfilService.mostrarUsuarioPerfil());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarUsuarioPerfil(@PathVariable Long id) {
        usuarioPerfilService.borrarUsuarioPerfil(id);
        return ResponseEntity.noContent().build();
    }

}
