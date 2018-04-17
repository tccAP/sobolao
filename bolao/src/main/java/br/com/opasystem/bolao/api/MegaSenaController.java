package br.com.opasystem.bolao.api;

import br.com.opasystem.bolao.dto.UsuarioView;
import br.com.opasystem.bolao.models.ConcursoMegaSena;
import br.com.opasystem.bolao.models.Usuario;
import br.com.opasystem.bolao.service.MegaSenaService;
import br.com.opasystem.bolao.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pauloho on 16/04/18.
 */
@Controller
@RequestMapping("/api/mega-sena")
@Transactional
public class MegaSenaController {
    private static final Logger LOG = LoggerFactory.getLogger(MegaSenaController.class);

    @Autowired
    private MegaSenaService megaSenaService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<ConcursoMegaSena> findLast() {
        return new ResponseEntity<ConcursoMegaSena>(megaSenaService.findLast(), HttpStatus.OK);
    }



    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ConcursoMegaSena> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<ConcursoMegaSena>(megaSenaService.findById(id), HttpStatus.OK);
    }

}
