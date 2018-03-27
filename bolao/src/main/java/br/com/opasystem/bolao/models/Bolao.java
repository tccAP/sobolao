package br.com.opasystem.bolao.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Entity
public class Bolao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dataSorteio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_organizador")
    private Usuario organizador;

    @ManyToMany
    @JoinTable(name = "participantes_bolao")
    private List<Usuario> participantes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(Date dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    @Override
    public String toString() {
        return "Bolao{" +
                "id=" + id +
                ", dataSorteio=" + dataSorteio +
                ", organizador=" + organizador +
                ", participantes=" + participantes +
                '}';
    }
}
