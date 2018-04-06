package br.com.opasystem.bolao.service;


import br.com.opasystem.bolao.models.Usuario;
import br.com.opasystem.bolao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
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

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> list() {
        return usuarioRepository.all();
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(criptoSenha(usuario));
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.update(criptoSenha(usuario));
    }

    public void delete(Usuario usuario) {
        usuarioRepository.remove(usuario);
    }

    public Usuario validateLogin(Usuario usuario){
        return usuarioRepository.validateLogin(usuario);
    }

    private Usuario criptoSenha(Usuario usuario) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(usuario.getSenha().getBytes()));
            usuario.setSenha(hash.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return usuario;
    }
}

