package br.com.opasystem.bolao.service;

import br.com.opasystem.bolao.models.ConcursoMegaSena;
import br.com.opasystem.bolao.repository.MegaSenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pauloho on 16/04/18.
 */
@Service
public class MegaSenaService {

    @Autowired
    private MegaSenaRepository megaSenaRepository;

    public ConcursoMegaSena findLast() {
        return megaSenaRepository.findLast();
    }

    public ConcursoMegaSena findById(Integer id) {
        return megaSenaRepository.findById(id);
    }

}
