package br.com.opasystem.bolao.controller;

import br.com.opasystem.bolao.models.Bolao;
import br.com.opasystem.bolao.service.BolaoService;
import br.com.opasystem.bolao.view.BoloesView;
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
@RequestMapping("/bolao")
@Transactional
public class BolaoController {

    @Autowired
    private BolaoService bolaoService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<List<Bolao>> list() {
        try {
            return new ResponseEntity<List<Bolao>>(bolaoService.list(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Bolao>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Bolao> findById(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<Bolao>(bolaoService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/findByOwner/{email}")
    public ResponseEntity<BoloesView> findByOwner(@PathVariable("email") String email) {
        System.out.println(email);
        try {
            BoloesView view = new BoloesView();
            view.setBoloes(bolaoService.findByOwner(email));

            return new ResponseEntity<BoloesView>(view, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<BoloesView>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity add(@RequestBody Bolao bolao) {
        try {
            bolaoService.save(bolao);
            return new ResponseEntity<Bolao>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/")
    public ResponseEntity<Bolao> update(@RequestBody Bolao bolao) {
        try {
            return new ResponseEntity<Bolao>(bolaoService.update(bolao), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Bolao> delete(@RequestBody Bolao bolao) {
        try {
            bolaoService.delete(bolao);
            return new ResponseEntity<Bolao>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bolao> delete(@PathVariable("id") Integer id) {
        try {
            bolaoService.delete(bolaoService.findById(id));
            return new ResponseEntity<Bolao>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Bolao>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
