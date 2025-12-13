package com.banksecure.exception;

public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException(String message) {
        super(message);
    }
    public DadosInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }

}