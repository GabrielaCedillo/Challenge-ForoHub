package com.alura.challenge.forohub.domain.usuarioPerfil;

import com.alura.challenge.forohub.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioPerfilRepository extends JpaRepository<Usuario,Long> {
}
