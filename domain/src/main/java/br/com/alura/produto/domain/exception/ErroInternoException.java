package br.com.alura.produto.domain.exception;

public class ErroInternoException extends RuntimeException {

    public ErroInternoException() {
        super("Erro interno");
    }
}
