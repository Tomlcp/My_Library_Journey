package com.library.exceptions;

//* Casos de uso: Livro já cadastrado, data de publicação errada
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}