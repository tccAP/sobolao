package br.com.bolao.model;

import java.util.Date;
import java.util.List;


public class Bolao {

    private Integer id;
    private Date dataSorteio;
    private Usuario organizador;
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
        return "Bolao: " + id;
    }
}
