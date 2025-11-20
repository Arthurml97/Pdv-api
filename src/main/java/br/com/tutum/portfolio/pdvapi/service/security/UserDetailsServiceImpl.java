package br.com.tutum.portfolio.pdvapi.service.security;

import br.com.tutum.portfolio.pdvapi.model.Usuario;
import br.com.tutum.portfolio.pdvapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

// @Service para marcar como a camada de serviço
// UserDetailsService diz ao spring que é a função para achar usuários
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método usado para o login, o SpringSecurity o chama quando alguém tenta logar, via token ou formulário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1 Passo: Usa o repositório para buscar o usuário pelo nome
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 2 Passo: Converter as permissões (ROLE) do nosso objeto para o formato que o Spring Security exige (GrantedAuthority)
        Collection<? extends GrantedAuthority> authorities = 
            Collections.singleton(new SimpleGrantedAuthority(usuario.getRole().name()));

        // 3 Passo: Converter o objeto usuário para o objeto interno do Spring, este objeto contém o username, a senha em HASH e a lista de permissÕes
        return new User (
            usuario.getUsername(), // Username para o login
            usuario.getPassword(), // Senha em HASH
            authorities            // ROLES (Supervisor e Atendente)
        );
    }
}
