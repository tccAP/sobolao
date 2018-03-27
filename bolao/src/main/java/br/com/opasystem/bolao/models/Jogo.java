package br.com.opasystem.bolao.models;

/**
 * Created by pauloho on 15/03/18.
 */
public enum Jogo {

    MEGA_SENA("MEGA-SENA");

    private String name;


    Jogo(String name) {
        this.name = name;
    }

    Jogo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
