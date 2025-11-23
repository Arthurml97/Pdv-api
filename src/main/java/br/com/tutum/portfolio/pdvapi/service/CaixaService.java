package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.dto.AbrirCaixaDTO;
import br.com.tutum.portfolio.pdvapi.model.Caixa;
import br.com.tutum.portfolio.pdvapi.model.StatusCaixa;
import br.com.tutum.portfolio.pdvapi.model.Usuario;
import br.com.tutum.portfolio.pdvapi.repository.CaixaRepository;
import br.com.tutum.portfolio.pdvapi.repository.UsuarioRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import br.com.tutum.portfolio.pdvapi.dto.SangriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Caixa abrirCaixa(String username, AbrirCaixaDTO dto) {
        // 1 Passo: Buscar o usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        // 2 Passo: Verificarse já existe um caixa ABERTO para este usuario
        Optional<Caixa> caixaAberto = caixaRepository.findByUsuarioAndStatus(usuario, StatusCaixa.ABERTO);

        if (caixaAberto.isPresent()) {
            throw new RegraDeNegocioException("Você já possui um caixa aberto. Feche-o antes de abrir um novo.");
        }

        // 3 Passo: Criar um novo caixa
        Caixa novoCaixa = new Caixa();
        novoCaixa.setUsuario(usuario);
        novoCaixa.setSaldoInicial(dto.getSaldoInicial());
        novoCaixa.setSaldoAtual(dto.getSaldoInicial()); // O saldo atual começa igual ao inicial
        novoCaixa.setStatus(StatusCaixa.ABERTO);

        return caixaRepository.save(novoCaixa);
    }

    public Caixa fecharCaixa(String username) {
        // 1 Passo: Buscar o caixa que está aberto
        Caixa caixa = buscarCaixaAberto(username);

        // 2 Passo: Atualizar os dados
        caixa.setDataFechamento(LocalDateTime.now());
        caixa.setStatus(StatusCaixa.FECHADO);

        // 3 Passo: Salvar
        return caixaRepository.save(caixa);
    }

    // Método auxiliar para usar com o VendaService
    public Caixa buscarCaixaAberto(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        return caixaRepository.findByUsuarioAndStatus(usuario, StatusCaixa.ABERTO)
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Não há caixa aberto para este usuário. Abra o caixa antes de vender"));
    }

    public Caixa realizarSangria(String username, SangriaDTO dto) {
        // 1 Passo: Buscar o caixa aberto do usuario
        Caixa caixa = buscarCaixaAberto(username);

        // 2 Passo: Validar o que é positivo
        if (dto.getValor() <= 0) {
            throw new RegraDeNegocioException("O valor da Sangria deve ser positivo.");
        }

        // 3 Passo: Validar se o caixa tem saldo suficiente
        if (caixa.getSaldoAtual() < dto.getValor()) {
            throw new RegraDeNegocioException(
                    "Saldo insuficiente para realizar a sangria. Saldo atual: " + caixa.getSaldoAtual());
        }

        // 4 Passo: Subtrair do saldo
        caixa.setSaldoAtual(caixa.getSaldoAtual() - dto.getValor());

        // 5 Passo: Salvar
        return caixaRepository.save(caixa);
    }
}
