package br.com.opasystem.bolao.dto;

import br.com.opasystem.bolao.models.Usuario;

/**
 * Created by pauloho on 06/04/18.
 */
public class UsuarioView {

    private Integer id;

    private String username;

    private String nome;

    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioView fromUsuario(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        return this;
    }
}
