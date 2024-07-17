package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.domain.usuarios.DatosAutenticacionUsuario;
import com.alura.challenge.forohub.domain.usuarios.Usuario;
import com.alura.challenge.forohub.utils.security.DatosJWTToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @PostMapping
    public ResponseEntity autenticacion(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.contrasena());
        var usuarioAuntenticado = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAuntenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
}
