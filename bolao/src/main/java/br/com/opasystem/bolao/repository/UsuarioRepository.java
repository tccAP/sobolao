package br.com.opasystem.bolao.repository;

import br.com.opasystem.bolao.models.Usuario;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public Usuario findByEmail(String email) {


        String h = "FROM Usuario usuario " +
                "WHERE usuario.email = :email";


        Query query = manager.createQuery(h);
        query.setParameter("email", email);
        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

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


    public Usuario validateLogin(Usuario usuario) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = builder.createQuery( Usuario.class );
        Root<Usuario> from = criteria.from( Usuario.class );

        CriteriaQuery<Usuario> select = criteria.select( from );

        if(usuario.getEmail() != null){
            select.where( builder.and(builder.equal( from.get("email"),usuario.getEmail() ),builder.equal( from.get("senha"),usuario.getSenha() )) );
        }else{
            select.where( builder.and(builder.equal( from.get("username"),usuario.getUsername() ),builder.equal( from.get("senha"),usuario.getSenha() )) );
        }


        TypedQuery<Usuario> typedQuery = manager.createQuery(select);
        Usuario user = typedQuery.getSingleResult();

        return user;
    }
}

