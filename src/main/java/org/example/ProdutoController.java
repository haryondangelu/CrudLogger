package org.exemple.controller;

import org.exemple.logger.AppLogger;
import org.exemple.model.Produto;
import org.exemple.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final AppLogger logger = AppLogger.getInstance();

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        logger.debug("Recebida requisição POST /produtos");
        Produto criado = service.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        logger.debug("Recebida requisição GET /produtos");
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        logger.debug("Recebida requisição GET /produtos/" + id);
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        logger.debug("Recebida requisição PUT /produtos/" + id);
        return service.atualizar(id, produto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.debug("Recebida requisição DELETE /produtos/" + id);
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}