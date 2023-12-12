package com.worker.service.exceptions;

public class LimiteIndisponivelException extends RuntimeException {
    public LimiteIndisponivelException(String message) {
        super(message);
    }
}
