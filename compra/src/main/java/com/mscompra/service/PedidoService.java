package com.mscompra.service;

import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import com.mscompra.service.exception.NegocioException;
import com.mscompra.service.rabbitmq.Producer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final Producer producer;

    public PedidoService(PedidoRepository repository, Producer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Pedido save(Pedido pedido) {
        pedido = repository.save(pedido);
        producer.producer(pedido);
        return pedido;
    }

    public Pedido findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NegocioException("Pedido não encontrado"));
    }

    public void delete(Long id) {
        Pedido pedido = findById(id);
        repository.deleteById(pedido.getId());
    }
}
