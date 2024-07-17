package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.domain.respuestas.DatosActualizarRespuesta;
import com.alura.challenge.forohub.domain.respuestas.DatosListadoRespuestas;
import com.alura.challenge.forohub.domain.respuestas.DatosRegistroRespuestas;
import com.alura.challenge.forohub.domain.respuestas.RespuestaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<DatosListadoRespuestas> agregarRespuesta(@RequestBody @Valid DatosRegistroRespuestas datosRegistroRespuesta) {

        DatosListadoRespuestas datosListadoRespuestas = respuestaService.agregarRespuesta(datosRegistroRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);

    }

    @PutMapping
    public ResponseEntity<DatosListadoRespuestas> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        DatosListadoRespuestas datosListadoRespuestas = respuestaService.actualizaRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(datosListadoRespuestas);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuestas>> mostrarRespuestas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(respuestaService.getRespuestas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoRespuestas> mostrarRespuesta(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.getRespuesta(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity borrarRespuesta(@PathVariable Long id) {
        respuestaService.deleteRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
