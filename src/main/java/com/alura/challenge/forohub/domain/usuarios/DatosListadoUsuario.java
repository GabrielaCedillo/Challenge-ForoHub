package com.alura.challenge.forohub.domain.usuarios;

public record DatosListadoUsuario(Long id,
                                  String nombre) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre());
    }
}
