package br.com.opasystem.bolao.repository;

import br.com.opasystem.bolao.models.Bolao;
import br.com.opasystem.bolao.models.Usuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager manager;

    public Usuario findById(Integer id) {
        return manager.find(Usuario.class, id);
    }

    public void remove(Usuario usuario) {
        manager.remove(usuario);
    }

    public Usuario update(Usuario usuario) {
        return manager.merge(usuario);
    }

    public void save(Usuario usuario) {
        manager.persist(usuario);
    }

    public List<Usuario> all() {
        return manager.createQuery("select usuario from Usuario usuario", Usuario.class).getResultList();
    }

}

