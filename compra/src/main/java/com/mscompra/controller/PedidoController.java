package com.mscompra.controller;

import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> salvar(@Valid @RequestBody Pedido pedido) {
        pedido = service.save(pedido);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable("id") Long id) {
        Pedido pedido = service.findById(id);
        return ResponseEntity.ok(pedido);
    }
}
