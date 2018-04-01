package br.com.opasystem.bolao.repository;

import br.com.opasystem.bolao.models.Aposta;
import br.com.opasystem.bolao.models.Bolao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Repository
public class BolaoRepository {
    @PersistenceContext
    private EntityManager manager;

    public Bolao findById(Integer id) {
        return manager.find(Bolao.class, id);
    }

    public  List<Bolao>  findByOwner(String email) {

        String h = "SELECT bolao FROM Bolao bolao " +
                "JOIN FETCH bolao.organizador " +
                "WHERE bolao.organizador.email = :email";

        Query query = manager.createQuery(h);
        query.setParameter("email",email);
        List results = query.getResultList();

        return (List<Bolao>) results;
    }

    public void remove(Bolao bolao) {
        manager.remove(bolao);
    }

    public Bolao update(Bolao bolao) {
        return manager.merge(bolao);
    }

    public void save(Bolao bolao) {
        manager.persist(bolao);
    }

    public List<Bolao> all() {
        return manager.createQuery("select b from Bolao b", Bolao.class).getResultList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BolaoRepository)) return false;

        BolaoRepository that = (BolaoRepository) o;

        return manager != null ? manager.equals(that.manager) : that.manager == null;
    }

    @Override
    public int hashCode() {
        return manager != null ? manager.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BolaoRepository{" +
                "manager=" + manager +
                '}';
    }
}

