package org.example.service;

import org.example.logger.AppLogger;
import org.example.model.Produto;
import org.example.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final AppLogger logger = AppLogger.getInstance();

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        logger.debug("Buscando todos os produtos no banco de dados.");
        List<Produto> produtos = repository.findAll();
        logger.info("Listagem de produtos concluída com sucesso. Total: " + produtos.size());
        return produtos;
    }

    public Optional<Produto> buscarPorId(Long id) {
        logger.debug("Buscando produto com ID: " + id);
        Optional<Produto> produto = repository.findById(id);
        if (produto.isEmpty()) {
            logger.erro("Produto com ID " + id + " não encontrado.");
        } else {
            logger.info("Produto com ID " + id + " retornado com sucesso.");
        }
        return produto;
    }

    public Produto salvar(Produto produto) {
        logger.debug("Iniciando persistência do produto: " + produto.getNome());
        Produto salvo = repository.save(produto);
        logger.info("Produto criado/atualizado com sucesso. ID Gerado: " + salvo.getId());
        return salvo;
    }

    public Optional<Produto> atualizar(Long id, Produto produtoAtualizado) {
        logger.debug("Tentando atualizar produto ID: " + id);
        return repository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            Produto atualizado = repository.save(produto);
            logger.info("Produto ID " + id + " atualizado com sucesso.");
            return atualizado;
        }).or(() -> {
            logger.erro("Falha na atualização. Produto ID " + id + " não existe.");
            return Optional.empty();
        });
    }

    public boolean deletar(Long id) {
        logger.debug("Tentando deletar produto ID: " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Produto ID " + id + " removido com sucesso.");
            return true;
        } else {
            logger.erro("Falha ao deletar. Produto ID " + id + " não foi encontrado.");
            return false;
        }
    }
}