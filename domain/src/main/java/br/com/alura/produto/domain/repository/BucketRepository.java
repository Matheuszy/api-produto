package br.com.alura.produto.domain.repository;

import br.com.alura.produto.domain.entity.Foto;

public interface BucketRepository {

    Foto armazenar(Foto foto);
}
