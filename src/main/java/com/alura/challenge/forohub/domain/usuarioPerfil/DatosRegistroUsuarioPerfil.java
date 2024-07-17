package com.alura.challenge.forohub.domain.usuarioPerfil;

import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuarioPerfil(@NotNull
                                         Long usuario_id,
                                         @NotNull
                                         Long perfil_id){
}
