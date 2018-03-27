package br.com.opasystem.bolao.models;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pauloho on 15/03/18.
 */
@Entity
public class Aposta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bolao_id")
    private Bolao bolao;

    private String jogo;

    private Date data;

    private String identificador;

    private Integer concurso;

    private Integer acertos;

    private BigDecimal valor;

    private String numeros;

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public Integer getConcurso() {
        return concurso;
    }

    public void setConcurso(Integer concurso) {
        this.concurso = concurso;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }


    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bolao getBolao() {
        return bolao;
    }

    public void setBolao(Bolao bolao) {
        this.bolao = bolao;
    }

    @Override
    public String toString() {
        return "Aposta{" +
                "id=" + id +
                ", bolao=" + bolao +
                ", jogo='" + jogo + '\'' +
                ", data=" + data +
                ", identificador='" + identificador + '\'' +
                ", concurso=" + concurso +
                ", acertos=" + acertos +
                ", valor=" + valor +
                ", numeros='" + numeros + '\'' +
                '}';
    }
}
