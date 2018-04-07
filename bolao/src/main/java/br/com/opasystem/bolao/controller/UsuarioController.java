package br.com.opasystem.bolao.controller;

import br.com.opasystem.bolao.dto.UsuarioView;
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

import java.util.ArrayList;
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
    public ResponseEntity<List<UsuarioView>> list() {
        try {
            List<Usuario> lst = usuarioService.list();
            List<UsuarioView> lstView = new ArrayList<>();
            for (Usuario user : lst) {
                lstView.add(user.toUsuarioView());
            }
            return new ResponseEntity<List<UsuarioView>>(lstView, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<UsuarioView>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<UsuarioView> findByEmail(@PathVariable("email") String email) {
        try {
            UsuarioView user = usuarioService.findByEmail(email).toUsuarioView();
            if (user != null) {
                return new ResponseEntity<UsuarioView>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<UsuarioView>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UsuarioView>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioView> findById(@PathVariable("id") Integer id) {
        try {

            UsuarioView user = usuarioService.findById(id).toUsuarioView();
            return new ResponseEntity<UsuarioView>(user, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<UsuarioView>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> add(@RequestBody Usuario usuario) {
        LOG.debug(usuario.toString());
        try {
            usuarioService.save(usuario);
            return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(usuario, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/validateLogin/")
    public ResponseEntity<UsuarioView> validateLogin(@RequestBody Usuario usuario) {
        try {
            UsuarioView userDatabase = usuarioService.validateLogin(usuario).toUsuarioView();
            return new ResponseEntity<UsuarioView>(userDatabase, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<UsuarioView>(HttpStatus.UNAUTHORIZED);
        }

    }


    @PutMapping("/")
    public ResponseEntity<UsuarioView> update(@RequestBody Usuario usuario) {
        try {
            UsuarioView user = usuarioService.update(usuario).toUsuarioView();
            return new ResponseEntity<UsuarioView>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<UsuarioView>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity delete(@RequestBody Usuario usuario) {
        try {
            usuarioService.delete(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        try {
            usuarioService.delete(usuarioService.findById(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
