package br.com.opasystem.bolao.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by pauloho on 09/04/18.
 */

@Entity
public class ConcursoMegaSena {

    @Id
    private Integer id;

    @ManyToMany
    @JoinTable(name = "numeros_sorteados_megasena")
    private List<NumeroMegaSena> numerosSorteados;

    private Date dataSorteio;

    private String acumulado;
    private Integer ganhadoresSena;
    private Integer ganhadoresQuina;
    private Integer ganhadoresQuadra;
    private BigDecimal valorAcumulado;
    private BigDecimal arrecadacaoTotal;
    private BigDecimal megaDaVirada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<NumeroMegaSena> getNumerosSorteados() {
        return numerosSorteados;
    }

    public void setNumerosSorteados(List<NumeroMegaSena> numerosSorteados) {
        this.numerosSorteados = numerosSorteados;
    }

    public Integer getGanhadoresSena() {
        return ganhadoresSena;
    }

    public void setGanhadoresSena(Integer ganhadoresSena) {
        this.ganhadoresSena = ganhadoresSena;
    }

    public Integer getGanhadoresQuina() {
        return ganhadoresQuina;
    }

    public void setGanhadoresQuina(Integer ganhadoresQuina) {
        this.ganhadoresQuina = ganhadoresQuina;
    }

    public Integer getGanhadoresQuadra() {
        return ganhadoresQuadra;
    }

    public void setGanhadoresQuadra(Integer ganhadoresQuadra) {
        this.ganhadoresQuadra = ganhadoresQuadra;
    }

    public BigDecimal getValorAcumulado() {
        return valorAcumulado;
    }

    public void setValorAcumulado(BigDecimal valorAcumulado) {
        this.valorAcumulado = valorAcumulado;
    }

    public BigDecimal getArrecadacaoTotal() {
        return arrecadacaoTotal;
    }

    public void setArrecadacaoTotal(BigDecimal arrecadacaoTotal) {
        this.arrecadacaoTotal = arrecadacaoTotal;
    }

    public BigDecimal getMegaDaVirada() {
        return megaDaVirada;
    }

    public void setMegaDaVirada(BigDecimal megaDaVirada) {
        this.megaDaVirada = megaDaVirada;
    }

    public Date getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(Date dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public String getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(String acumulado) {
        this.acumulado = acumulado;
    }
}
