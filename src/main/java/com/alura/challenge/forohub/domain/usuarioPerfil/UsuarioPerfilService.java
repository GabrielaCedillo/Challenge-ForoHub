package com.alura.challenge.forohub.domain.usuarioPerfil;

import com.alura.challenge.forohub.domain.perfiles.IPerfilRepository;
import com.alura.challenge.forohub.domain.perfiles.Perfil;
import com.alura.challenge.forohub.domain.usuarios.IUsuarioRepository;
import com.alura.challenge.forohub.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioPerfilService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPerfilRepository perfilRespository;

    @Autowired
    private IUsuarioPerfilRepository usuarioPerfilRepository;

    public DatosListadoUsuarioPerfil agregarPefil(DatosRegistroUsuarioPerfil datosRegistroUsuarioPerfil) {

        Usuario usuario;
        Perfil perfil;

        if (usuarioRepository.findById(datosRegistroUsuarioPerfil.usuario_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontró el usuario");
        }
        if (perfilRespository.findById(datosRegistroUsuarioPerfil.perfil_id()).isEmpty()) {
            throw new ErrorDeConsulta("No se encontró el perfil");
        }

        usuario = usuarioRepository.getReferenceById(datosRegistroUsuarioPerfil.usuario_id());
        perfil = perfilRespository.getReferenceById(datosRegistroUsuarioPerfil.perfil_id());

        UsuarioPerfil usuarioPerfil = new UsuarioPerfil(null, usuario, perfil);

        UsuarioPerfil usuarioPerfil1 = usuarioPerfilRepository.save(usuarioPerfil);

        return new DatosListadoUsuarioPerfil(usuarioPerfil1);
    }

    public List<DatosListadoUsuarioPerfil> mostrarUsuarioPerfil() {
        return usuarioPerfilRepository.findAll().stream().map(DatosListadoUsuarioPerfil::new).toList();
    }

    public void borrarUsuarioPerfil(Long id) {
        usuarioPerfilRepository.deleteById(id);
    }
}
