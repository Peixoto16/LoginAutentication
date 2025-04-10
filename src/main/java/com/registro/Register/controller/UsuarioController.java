package com.registro.Register.controller;

import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.model.LoginUsuario;
import com.registro.Register.model.Usuario;
import com.registro.Register.repository.UsuarioRepository;
import com.registro.Register.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<UsuarioResponse> listaDeUsuarios() {
        List<UsuarioResponse> listaUsuarios = service.listarUsuarios();
        return ResponseEntity.ok(listaUsuarios).getBody();
    }

    @PostMapping
    public UsuarioResponse criarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        UsuarioResponse user = service.criarUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user).getBody();
    }

    @PutMapping
    public UsuarioResponse editarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        UsuarioResponse editarUser = service.editarUsuario(usuarioRequest);
        return ResponseEntity.ok(editarUser).getBody();
    }

    @DeleteMapping("/{id}")
    public Object deletarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuario loginRequest) {

        if (!repository.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email não cadastrado");
        }

        return ResponseEntity.ok("Logado");
    }
}
