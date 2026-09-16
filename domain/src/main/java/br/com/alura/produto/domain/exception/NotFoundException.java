package br.com.alura.produto.domain.exception;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> clazz) {
        super(format("Recurso %s não econtrado", clazz.getSimpleName()));
    }
}
