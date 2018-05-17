package br.com.bolao.model;

/**
 * Created by pauloho on 27/03/18.
 */

public class Usuario {

    private Integer id;

    private String username;

    private String name;

    private String email;

    private String senha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nome='" + name + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public Usuario(Integer id, String username, String nome, String email) {
        this.id = id;
        this.username = username;
        this.name = nome;
        this.email = email;
    }

    public Usuario() {
    }
}
