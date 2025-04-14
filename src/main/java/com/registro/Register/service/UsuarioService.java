package com.registro.Register.service;

import com.registro.Register.config.UsuarioMapper;
import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.model.LoginUsuario;
import com.registro.Register.model.Usuario;
import com.registro.Register.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper mapper;

    private PasswordEncoder encoder;
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.encoder = new BCryptPasswordEncoder();
        this.repository = repository;
    }

    public List<UsuarioResponse> listarUsuarios() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) {

        String passEncoder = this.encoder.encode(usuarioRequest.getSenha());
        usuarioRequest.setSenha(passEncoder);

        Usuario user = mapper.toEntity(usuarioRequest);
        repository.save(user);
        return mapper.toResponse(user);
    }

    public UsuarioResponse editarUsuario(UsuarioRequest usuarioRequest) {
        String passEncoder = this.encoder.encode(usuarioRequest.getSenha());
        usuarioRequest.setSenha(passEncoder);

        Usuario user = repository.findByEmail(usuarioRequest.getEmail());

            user.setNome(usuarioRequest.getNome());
            user.setEmail(usuarioRequest.getEmail());
            user.setSenha(usuarioRequest.getSenha());
            user.setTelefone(usuarioRequest.getTelefone());
            user.setAtivo(usuarioRequest.isAtivo());

        repository.save(user);

        return mapper.toResponse(user);
    }

    public Boolean validarSenha(LoginUsuario loginRequest) {

        Usuario senha = repository.findByEmail(loginRequest.getEmail());

        return encoder.matches(loginRequest.getSenha(), senha.getSenha());
    }
}
