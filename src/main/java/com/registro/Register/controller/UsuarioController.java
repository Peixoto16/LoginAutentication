package com.registro.Register.controller;

import com.registro.Register.configSecurity.JwtUtil;
import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.dto.LoginUsuario;
import com.registro.Register.repository.UsuarioRepository;
import com.registro.Register.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        if (repository.existsByEmail(usuarioRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        UsuarioResponse novoUsuario = service.criarUsuario(usuarioRequest);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        UsuarioResponse editarUser = service.editarUsuario(usuarioRequest);
        return ResponseEntity.ok(editarUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuario loginRequest) {

        if (!repository.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email não cadastrado");
        }

        Boolean validarSenha = service.validarSenha(loginRequest);

        if (!validarSenha) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Senha Incorreta");
        }

        String token = jwtUtil.generateToken(loginRequest.getEmail());
        return ResponseEntity.ok(Map.of("token", token));

    }


}
