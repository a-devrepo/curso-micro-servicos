package com.workercompras.service;

import com.workercompras.model.Endereco;
import com.workercompras.repository.CepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CepService {
    private final CepRepository repository;

    public CepService(CepRepository repository) {
        this.repository = repository;
    }

    public void buscarCep(String cep) {
        Endereco endereco = repository.buscaPorCep(cep);
        log.info("Endereco montado com sucesso: {}", endereco);
    }
}
