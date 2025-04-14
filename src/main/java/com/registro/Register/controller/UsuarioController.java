package com.registro.Register.controller;

import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.model.LoginUsuario;
import com.registro.Register.repository.UsuarioRepository;
import com.registro.Register.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        if (repository.existsByEmail(usuarioRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        UsuarioResponse novoUsuario = service.criarUsuario(usuarioRequest);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping
    public UsuarioResponse editarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        UsuarioResponse editarUser = service.editarUsuario(usuarioRequest);
        return ResponseEntity.ok(editarUser).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuario loginRequest) {

        if (!repository.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email não cadastrado");
        }

        Boolean validarSenha = service.validarSenha(loginRequest);

        if(!validarSenha){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Senha Incorreta");
        }

        return ResponseEntity.ok("Logado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // Corrigido o cast
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
