package com.worker.service;

import com.worker.model.Cartao;
import com.worker.model.Pedido;
import com.worker.service.exceptions.LimiteIndisponivelException;
import com.worker.service.exceptions.SaldoInsuficienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidadorService {

    private EmailService service;

    public ValidadorService(EmailService service) {
        this.service = service;
    }

    public void validarPedido(Pedido pedido) {
        validarLimiteDisponivel(pedido.getCartao());
        validarCompraComLimite(pedido);
        service.notificarCliente(pedido);
    }

    private void validarCompraComLimite(Pedido pedido) {
        if (pedido.getValor().longValue() > pedido.getCartao().getLimiteDisponivel().longValue()) {
            log.error("Valor do pedido: {}. Limite disponível: {}", pedido.getValor(), pedido.getCartao().getLimiteDisponivel());
            throw new SaldoInsuficienteException("Você não tem limite para efetuar essa compra");
        }
    }

    private void validarLimiteDisponivel(Cartao cartao) {
        if (cartao.getLimiteDisponivel().longValue() <= 0) {
            log.error("Limite indisponível");
            throw new LimiteIndisponivelException("Limite indisponível");
        }
    }
}
