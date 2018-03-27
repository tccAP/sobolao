package br.com.opasystem.bolao.controller;


import br.com.opasystem.bolao.models.Aposta;
import br.com.opasystem.bolao.models.Bolao;
import br.com.opasystem.bolao.service.ApostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by pauloho on 15/03/18.
 */
@Controller
@RequestMapping("/aposta")
@Transactional
public class ApostaController {

    @Autowired
    private ApostaService apostaService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<List<Aposta>> list() {
        try {
            return new ResponseEntity<List<Aposta>>(apostaService.list(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Aposta>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Aposta> list(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<Aposta>(apostaService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Aposta>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/findByBolao/{id}")
    public ResponseEntity<List<Aposta>> findByBolao(@PathVariable("id") Integer id) {
        System.out.println("------>   "+id);
        try {
            return new ResponseEntity<List<Aposta>>(apostaService.findByBolao(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Aposta>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/")
    public ResponseEntity add(@RequestBody Aposta aposta) {
        try {
            apostaService.save(aposta);
            return new ResponseEntity<Bolao>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/")
    public ResponseEntity<Aposta> update(@RequestBody Aposta aposta) {
        apostaService.update(aposta);
        try {
            apostaService.update(aposta);
            return new ResponseEntity<Aposta>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/")
    public ResponseEntity delete(@RequestBody Aposta aposta) {
        try {
            apostaService.delete(aposta);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        try {
            apostaService.delete(apostaService.findById(id));
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
