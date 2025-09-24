package com.library.exceptions;

//* Casos de uso: livro, autor ou editora não encontrada
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}