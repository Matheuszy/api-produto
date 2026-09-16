package br.com.alura.produto.infra.mapper;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.infra.msg.ProdutoMsg;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface ProdutoMsgMapper {

    ProdutoMsg converter(Produto source);
}
