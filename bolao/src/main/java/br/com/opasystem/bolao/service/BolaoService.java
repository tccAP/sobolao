package br.com.opasystem.bolao.service;


import br.com.opasystem.bolao.models.Bolao;
import br.com.opasystem.bolao.repository.BolaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Service
public class BolaoService {


    @Autowired
    private BolaoRepository bolaoRepository;


    public Bolao findById(Integer id) {
        return bolaoRepository.findById(id);
    }

    public List<Bolao> findByOwner(String email) {
        return bolaoRepository.findByOwner(email);
    }

    public List<Bolao> findByParticipant(String email) {
        return bolaoRepository.findByParticipant(email);
    }

    public List<Bolao> list() {
        return bolaoRepository.all();
    }

    public void save(Bolao bolao) {
        bolaoRepository.save(bolao);
    }

    public Bolao update(Bolao bolao) {
        return bolaoRepository.update(bolao);
    }

    public void delete(Bolao bolao) {
        bolaoRepository.remove(bolao);
    }


}

