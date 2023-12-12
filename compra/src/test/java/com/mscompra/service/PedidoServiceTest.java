package com.mscompra.service;


import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock
    private PedidoRepository repository;
    @Mock
    private Producer producer;
    @InjectMocks
    PedidoService service;
    DadosMock dadosMock = new DadosMock();

    @DisplayName("Salvar pedido com sucesso")
    @Test
    void deveSalvarUmPedidoComSucesso() {
        var pedidoMock = dadosMock.getPedido();
        when(repository.save(any(Pedido.class))).thenReturn(pedidoMock);
        doNothing().when(producer).producer(any(Pedido.class));

        var pedidoSalvo = service.save(pedidoMock);
        assertEquals(pedidoMock.getCep(), pedidoSalvo.getCep());
        assertNotNull(pedidoSalvo.getCep());
    }


}