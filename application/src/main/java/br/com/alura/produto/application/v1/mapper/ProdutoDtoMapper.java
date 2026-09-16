package br.com.alura.produto.application.v1.mapper;

import br.com.alura.produto.application.v1.dto.ProdutoDto;
import br.com.alura.produto.domain.entity.Produto;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface ProdutoDtoMapper {

    Produto converter(ProdutoDto.Request source);

    ProdutoDto.Response converter(Produto source);

    ProdutoDto.Representacao converterParaRepresentacao(Produto source);
}
