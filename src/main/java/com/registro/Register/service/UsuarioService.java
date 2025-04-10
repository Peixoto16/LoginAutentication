package com.registro.Register.service;

import com.registro.Register.config.UsuarioMapper;
import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.model.Usuario;
import com.registro.Register.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper mapper;

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponse> listarUsuarios() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) {
        Usuario user = mapper.toEntity(usuarioRequest);
        repository.save(user);
        return mapper.toResponse(user);
    }
}
