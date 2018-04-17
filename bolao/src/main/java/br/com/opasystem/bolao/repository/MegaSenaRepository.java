package br.com.opasystem.bolao.repository;

import br.com.opasystem.bolao.models.ConcursoMegaSena;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by pauloho on 16/04/18.
 */
@Repository
public class MegaSenaRepository {

    @PersistenceContext
    private EntityManager manager;


    public ConcursoMegaSena findLast() {

        String jpql = "SELECT concurso FROM ConcursoMegaSena concurso ORDER BY concurso.id DESC";
        TypedQuery<ConcursoMegaSena> query = manager.createQuery(jpql, ConcursoMegaSena.class);
        query.setMaxResults(1);

        return query.getSingleResult();
    }

    public ConcursoMegaSena findById(Integer id) {

        return manager.find(ConcursoMegaSena.class, id);

    }
}