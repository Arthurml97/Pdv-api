package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.model.Usuario;
import br.com.tutum.portfolio.pdvapi.repository.UsuarioRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção da ferramenta de Hash

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Primeiro passo: Regra de não permitir dois usuarios com mesmo login
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente.isPresent()) {
            throw new RegraDeNegocioException("Já existe um usuário com este login.");
        }
        // Segundo passso: Criptografia das senhas, pega a senha, criptografa e volta no objeto
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);

        // Terceiro passo: Salva no banco, porém agora seguro
        return usuarioRepository.save(usuario);
    }
}
