package com.mscompra;

import com.mscompra.model.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DadosMock {
    public Pedido getPedido() {
        return Pedido.builder()
                .valor(BigDecimal.TEN)
                .nome("Alison").produto(1L)
                .cpfCliente("111.111.111-11")
                .cep("20000000")
                .dataCompra(new Date())
                .email("study.javaweb11@gmail.com")
                .build();
    }
}
