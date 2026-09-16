package br.com.alura.produto.domain.repository;

import br.com.alura.produto.domain.entity.Produto;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProdutoRepository extends CrudRepository<Produto, UUID> {

}
