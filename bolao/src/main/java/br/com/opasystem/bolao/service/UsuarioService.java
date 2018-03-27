package br.com.opasystem.bolao.service;


import br.com.opasystem.bolao.models.Bolao;
import br.com.opasystem.bolao.models.Usuario;
import br.com.opasystem.bolao.repository.BolaoRepository;
import br.com.opasystem.bolao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> list() {
        return usuarioRepository.all();
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.update(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.remove(usuario);
    }


}

