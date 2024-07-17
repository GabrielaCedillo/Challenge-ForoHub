package com.alura.challenge.forohub.domain.topicos;

import com.alura.challenge.forohub.domain.cursos.Curso;
import com.alura.challenge.forohub.domain.cursos.ICursoRepository;
import com.alura.challenge.forohub.domain.respuestas.DTopicoRespuestas;
import com.alura.challenge.forohub.domain.usuarios.IUsuarioRepository;
import com.alura.challenge.forohub.domain.usuarios.Usuario;
import com.alura.challenge.forohub.utils.error.ErrorDeConsulta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TopicoService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ICursoRepository cursoRespository;
    @Autowired
    private ITopicoRepository topicoRepository;

    public DatosListadoTopico agregarTopico(DatosRegistroTopico datosRegistroTopico) {
        Curso curso;
        Usuario usuario;

        if (!usuarioRepository.existsById(datosRegistroTopico.usuario_id())) {
            throw new ErrorDeConsulta("El usuario NO fue encontrado");
        }

        if (cursoRespository.findByNombre(datosRegistroTopico.nombreCurso()).isPresent()) {
            curso = cursoRespository.findByNombre(datosRegistroTopico.nombreCurso()).get();
        } else {
            throw new ErrorDeConsulta("El curso NO fue encontrado ");
        }

        usuario = usuarioRepository.findById(datosRegistroTopico.usuario_id()).get();

        Topico topico = new Topico(
                null,
                datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje(),
                LocalDateTime.now(),
                true,
                usuario,
                curso,
                new ArrayList<>()
        );

        Topico topicoR = topicoRepository.save(topico);
        return new DatosListadoTopico(
                topicoR.getId(),
                topicoR.getTitulo(),
                topicoR.getMensaje(),
                topicoR.getFecha()
        );
    }

    public Page<DatosListadoTopico> getTopicos(Pageable pageable) {
        return topicoRepository.findAllByStatusTrue(pageable).map(DatosListadoTopico::new);
    }

    public DatosListadoTopico getTopico(Long id) {
        if (topicoRepository.findByStatusById(id) == null) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        if (!topicoRepository.findByStatusById(id)) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        if (topicoRepository.findById(id).isEmpty()) {
            throw new ErrorDeConsulta("No se halló el tópico");
        }
        Topico topicoR = topicoRepository.findById(id).get();

        return new DatosListadoTopico(
                topicoR.getId(),
                topicoR.getTitulo(),
                topicoR.getMensaje(),
                topicoR.getFecha()
        );
    }

    @Transactional
    public DatosListadoTopico actualizaTopico(DatosActualizarTopico datosActualizarTopico) {
        if (!topicoRepository.existsById(datosActualizarTopico.id())) {
            throw new ErrorDeConsulta("No se halló el topico");
        }
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarTopico(datosActualizarTopico);
        return new DatosListadoTopico(topico);
    }

    @Transactional
    public void desactivaTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ErrorDeConsulta("No se halló el topico");
        }
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivaTopico();
    }

    public Page<DTopicoRespuestas> getRespuestas(Long id, Pageable pageable) {
        if (!topicoRepository.existsById(id) || !topicoRepository.findByStatusById(id)) {
            throw new ErrorDeConsulta("No se halló el topico");
        }
        Page<DTopicoRespuestas> dtOpicoRespuestas = topicoRepository.findAllByRespuestas(id, pageable).map(DTopicoRespuestas::new);
        return dtOpicoRespuestas;
    }
}
