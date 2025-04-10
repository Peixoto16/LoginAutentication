package com.registro.Register.config;

import com.registro.Register.dto.UsuarioRequest;
import com.registro.Register.dto.UsuarioResponse;
import com.registro.Register.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {

        return new Usuario(
                request.getNome(),
                request.getEmail(),
                request.getSenha(),
                request.getTelefone());
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getDataCriacao(),
                usuario.isAtivo()
        );
    }


}
