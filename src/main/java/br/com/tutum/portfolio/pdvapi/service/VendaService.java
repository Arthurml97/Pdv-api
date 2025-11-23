package br.com.tutum.portfolio.pdvapi.service;

import br.com.tutum.portfolio.pdvapi.model.ItemVenda;
import br.com.tutum.portfolio.pdvapi.model.Produto;
import br.com.tutum.portfolio.pdvapi.model.Usuario;
import br.com.tutum.portfolio.pdvapi.model.Venda;
import br.com.tutum.portfolio.pdvapi.model.StatusVenda;
import br.com.tutum.portfolio.pdvapi.model.Caixa;
import br.com.tutum.portfolio.pdvapi.model.FormaPagamento;
import br.com.tutum.portfolio.pdvapi.repository.ProdutoRepository;
import br.com.tutum.portfolio.pdvapi.repository.UsuarioRepository;
import br.com.tutum.portfolio.pdvapi.repository.VendaRepository;
import br.com.tutum.portfolio.pdvapi.repository.CaixaRepository;
import br.com.tutum.portfolio.pdvapi.service.exception.RegraDeNegocioException;
import br.com.tutum.portfolio.pdvapi.dto.AdicionarItemDTO;
import br.com.tutum.portfolio.pdvapi.dto.FinalizarVendaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Função importante para garantir a integridade

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private CaixaService caixaService;

    // Cria uma nova venda vazia para o usuário atual

    public Venda abrirVenda(String usernameUsuario) {
        Usuario usuario = usuarioRepository.findByUsername(usernameUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        Caixa caixa = caixaService.buscarCaixaAberto(usernameUsuario);

        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setCaixa(caixa);

        return vendaRepository.save(venda);
    }

    // Adiciona um item a uma venda já existente

    @Transactional // Função para garantir caso de erro, desfaz a operação toda
    public Venda adicionarItem(Long idVenda, AdicionarItemDTO dto) {
        // 1 Passo: Buscar a venda
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));

        // 2 Passo: Buscar o produto pelo código de barras
        Produto produto = produtoRepository.findByCodigoDeBarras(dto.getCodigoDeBarras())
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado"));

        // 3 Passo: Criar o ItemVenda
        ItemVenda item = new ItemVenda();
        item.setQuantidade(dto.getQuantidade());
        item.setProduto(produto);
        item.setPrecoUnitario(produto.getValorUnitario()); // Pega o preço de agora, do momento
        item.setVenda(venda);

        // 4 Passo: Adicionar na lista da venda
        venda.getItens().add(item);

        // 5 Passo: Recalcula o total da venda, pega o total atual +
        // (quantidade*preçodoitemnovo)
        double subtotalItem = item.getQuantidade() * item.getPrecoUnitario();
        venda.setValorTotal(venda.getValorTotal() + subtotalItem);

        // 6 Passo: Salva tudo, e com o CascadeType.ALL (em Venda.java), salva o item
        // também
        return vendaRepository.save(venda);
    }

    @Transactional
    public Venda removerItem(Long idVenda, Long idItem) {

        // 1 Passo: Buscar a venda
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));

        // 2 Passo: Buscar o item dentro da lista daquela venda, usando o stream do Java
        // para filtrar a lista em memória
        ItemVenda itemParaRemover = venda.getItens().stream()
                .filter(item -> item.getId().equals(idItem))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Item não encontrado nesta venda"));

        // 3 Passo: Subtrair o item do Total da venda
        double valorItem = itemParaRemover.getSubTotal();
        venda.setValorTotal(venda.getValorTotal() - valorItem);

        // 4 Passo: Remover o item da lista e do banco
        venda.getItens().remove(itemParaRemover);

        // 5 Passo: Salvar a venda para atualizar o total
        return vendaRepository.save(venda);
    }

    @Transactional
    public Venda finalizarVenda(Long idVenda, FinalizarVendaDTO dto) {

        // 1 Passo: Buscar a venda
        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RegraDeNegocioException("Venda não encontrada"));

        // 2 Passo: Saber se a venda ja foi fechada
        if (venda.getStatus() != StatusVenda.ABERTA) {
            throw new RegraDeNegocioException("Esta venda ja foi finalizada ou cancelada");
        }

        // 3 Passo: Atualização dos dados
        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setStatus(StatusVenda.FINALIZADA);

        // 4 Passo: Atualizar o saldo do caixa
        if (dto.getFormaPagamento() == FormaPagamento.DINHEIRO) {
            Caixa caixa = venda.getCaixa(); // Pega o caixa vinculado a esta venda

            // 4.1 Passo: Somar o total da venda no saldo atual do caixa
            double novoSaldo = caixa.getSaldoAtual() + venda.getValorTotal();
            caixa.setSaldoAtual(novoSaldo);

            caixaRepository.save(caixa);
        }

        // 5 Passo: Salvar
        return vendaRepository.save(venda);
    }
}
