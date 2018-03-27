package br.com.opasystem.bolao.service;

import br.com.opasystem.bolao.models.Aposta;
import br.com.opasystem.bolao.repository.ApostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;


    public Aposta findById(Integer id) {
        return apostaRepository.findById(id);
    }

    public List<Aposta> findByBolao(Integer id) {
        return apostaRepository.findByBolao(id);
    }

    public List<Aposta> list() {
        return apostaRepository.all();
    }

    public void save(Aposta aposta) {
        apostaRepository.save(aposta);
    }

    public void update(Aposta aposta) {
        apostaRepository.update(aposta);
    }

    public void delete(Aposta aposta) {
        apostaRepository.remove(aposta);
    }

    public void deleteById(Integer id) {
        apostaRepository.deleteById(id);
    }


}
