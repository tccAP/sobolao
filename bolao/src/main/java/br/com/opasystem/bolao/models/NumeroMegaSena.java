package br.com.opasystem.bolao.models;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by pauloho on 09/04/18.
 */
public class NumeroMegaSena {


    @Id
    private Integer id;

    @ManyToMany
    @JoinTable(name = "numeros_concurso")
    private List<ConcursoMegaSena> concursos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ConcursoMegaSena> getConcursos() {
        return concursos;
    }

    public void setConcursos(List<ConcursoMegaSena> concursos) {
        this.concursos = concursos;
    }
}
