package br.com.opasystem.bolao.repository;


import br.com.opasystem.bolao.models.Aposta;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ApostaRepository {

    @PersistenceContext
    private EntityManager manager;

    public Aposta findById(Integer id) {
        return manager.find(Aposta.class, id);
    }

    public List<Aposta> findByBolao(Integer id) {
/*

        "select pos " +
                "from ProcessoOs pos " +
                "  left join fetch pos.ordemServico "  +
                "  left join fetch pos.situacao "  +
                "where pos.responsavel.id = " + f.getId()   +
                " and (pos.situacao.tipo = 'N' or pos.situacao.tipo = null)"  +
                " and pos.ordemServico.status = 'P' " +
                "order by pos.dataFim, pos.nivel"
*/


        String h = "SELECT aposta FROM Aposta aposta " +
                "JOIN FETCH aposta.bolao " +
                "WHERE aposta.bolao.id = :id";


        Query query = manager.createQuery(h);
        query.setParameter("id",id);
        List results = query.getResultList();

        return (List<Aposta>) results;
    }

    public void remove(Aposta aposta) {
        manager.remove(aposta);
    }

    public void deleteById(Integer id) {
        this.remove(this.findById(id));
    }

    public void update(Aposta aposta) {
        manager.merge(aposta);
    }

    public void save(Aposta aposta) {
        manager.persist(aposta);
    }

    public List<Aposta> all() {
        return manager.createQuery("select a from Aposta a", Aposta.class).getResultList();
    }

    @Override
    public String toString() {
        return "ApostaRepository{" +
                "manager=" + manager +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApostaRepository)) return false;

        ApostaRepository that = (ApostaRepository) o;

        return manager != null ? manager.equals(that.manager) : that.manager == null;
    }

    @Override
    public int hashCode() {
        return manager != null ? manager.hashCode() : 0;
    }
}
