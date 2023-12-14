package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.CompraApplication;
import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import com.mscompra.service.rabbitmq.Producer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PedidoService service;
    @MockBean
    private Producer producer;

    private DadosMock dadosMock = new DadosMock();
    @Autowired
    private ObjectMapper mapper;
    private static final String ROTA_PEDIDO = "/pedido";

    @DisplayName("POST - Deve cadastrar um novo pedido com sucesso no banco de dados")
    @Test
    @Order(1)
    void devecadastrarPedidoComSucesso() throws Exception {
        var pedidoBody = dadosMock.getPedido();
        var id = 1L;

        doNothing().when(producer).producer(any(Pedido.class));

        mockMvc.perform(post(ROTA_PEDIDO)
                        .content(mapper.writeValueAsString(pedidoBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Pedido pedidoSalvo = service.findById(id);

        assertEquals(pedidoSalvo.getId(), id);
        assertNotNull(pedidoSalvo);
    }

    @DisplayName("GET - Deve buscar um pedido com sucesso no banco de dados")
    @Test
    @Order(2)
    void deveBuscarPedidoComSucesso() throws Exception {
        var id = 1L;

        mockMvc.perform(get(ROTA_PEDIDO + "/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("GET - Deve falhar ao buscar um pedido que não existe")
    @Test
    @Order(3)
    void deveFalharAoBuscarPedido() throws Exception {
        var id = 2L;

        mockMvc.perform(get(ROTA_PEDIDO + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("Delete - Deve excluir pedido com sucesso")
    @Test
    @Order(4)
    void deveExcluirPedidoComSucesso() throws Exception {
        var id = 1L;

        ResultActions resultActions = mockMvc.perform(delete(ROTA_PEDIDO + "/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());

        Throwable exception = assertThrows(EntidadeNaoEncontradaException.class, () -> service.delete(id));

        assertEquals("Pedido não encontrado", exception.getMessage());
    }
}