package com.library.exceptions;

//* Casos de uso: parâmetros das colunas não concordantes com o tipo de dado
public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }
}