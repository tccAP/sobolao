package br.com.opasystem.bolao.controller;

import br.com.opasystem.bolao.models.Usuario;
import br.com.opasystem.bolao.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/usuario")
@Transactional
public class UsuarioController {
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<List<Usuario>> list() {
        try {
            return new ResponseEntity<List<Usuario>>(usuarioService.list(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Usuario>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable("email") String email) {
        try {
            Usuario user = usuarioService.findByEmail(email);
            if (user != null) {
                return new ResponseEntity<Usuario>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<Usuario>(user, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<Usuario>(usuarioService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> add(@RequestBody Usuario usuario) {
        LOG.debug(usuario.toString());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(usuario.toString());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        try {
            usuarioService.save(usuario);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println(usuario.toString());
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            return new ResponseEntity<Usuario>(usuario,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERRO ERRO ERRO ERRO ERRO ");
            return new ResponseEntity<Usuario>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/")
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        try {
            return new ResponseEntity<Usuario>(usuarioService.update(usuario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Usuario> delete(@RequestBody Usuario usuario) {
        try {
            usuarioService.delete(usuario);
            return new ResponseEntity<Usuario>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable("id") Integer id) {
        try {
            usuarioService.delete(usuarioService.findById(id));
            return new ResponseEntity<Usuario>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
