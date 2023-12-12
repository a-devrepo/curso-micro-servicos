package com.mscompra.service;


import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.exception.NegocioException;
import com.mscompra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @DisplayName("Deve falhar na busca de pedido que não existe")
    @Test
    void deveFalharNaBuscaDePedidoNaoExistente() {
        var id = 1L;
        Throwable exception = assertThrows(NegocioException.class, () -> {
            service.findById(id);
        });
        assertEquals("Pedido não encontrado", exception.getMessage());
    }

    @DisplayName("Deve Buscar Um Pedido Com Sucesso Na Base De Dados")
    @Test
    void deveBuscarUmPedidoComSucesso() {
        var pedidoMock = dadosMock.getPedido();
        pedidoMock.setId(1L);
        var id = 1l;
        when(repository.findById(anyLong())).thenReturn(Optional.of(pedidoMock));

        var pedidoSalvo = service.findById(id);
        assertNotNull(pedidoSalvo);
        assertEquals(pedidoMock.getId(), pedidoSalvo.getId());
        verify(repository,atLeastOnce()).findById(id);
    }
}