package br.com.alura.produto.domain.repository;

import br.com.alura.produto.domain.entity.Produto;

public interface NotificacaoRepository {

    void notificar(Produto produto);
}
