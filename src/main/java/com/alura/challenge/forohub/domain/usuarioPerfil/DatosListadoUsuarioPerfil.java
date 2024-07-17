package com.alura.challenge.forohub.domain.usuarioPerfil;

public record DatosListadoUsuarioPerfil(String nomrbreUsuario,
                                        String nombrePerfil) {
    public DatosListadoUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        this(usuarioPerfil.getUsuario().getNombre(), usuarioPerfil.getPerfil().getNombre());
    }
}
