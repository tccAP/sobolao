package br.com.opasystem.bolao.models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by pauloho on 09/04/18.
 */

@Entity
public class NumeroMegaSena {

    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
