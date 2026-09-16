package br.com.alura.produto.application.v1.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static br.com.alura.produto.application.v1.dto.ProdutoDtoFactory.criarProdutoDtoRequest;
import static br.com.alura.produto.application.v1.dto.assertions.ProdutoDtoAssertions.afirmaQue_ProdutoDto_Response;
import static br.com.alura.produto.domain.entity.assertions.ProdutoAssertions.afirmaQue_Produto;
import static br.com.alura.produto.domain.entity.factory.ProdutoFactory.criarProduto;
import static org.mapstruct.factory.Mappers.getMapper;

class ProdutoDtoMapperTest {

    private static final ProdutoDtoMapper mapper = getMapper(ProdutoDtoMapper.class);

    @DisplayName("Quando converter ProdutoDto.Request para Produto")
    @Nested
    class Converter1 {

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {

            @DisplayName("Dado um ProdutoDto.Request com todos os campos")
            @Test
            void test1() {
                // Given
                var source = criarProdutoDtoRequest()
                        .comTodosOsCampos();
                // When
                var atual = mapper.converter(source);
                // Then
                afirmaQue_Produto(atual)
                        .foiConvertidoDe_ProdutoDto_Request();
            }
        }
    }

    @DisplayName("Quando converter Produto para ProdutoDto.Request")
    @Nested
    class Converter2 {

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {

            @DisplayName("Dado um Produto com todos os campos")
            @Test
            void test1() {
                // Given
                var source = criarProduto()
                        .comTodosOsCampos();
                // When
                var atual = mapper.converter(source);
                // Then
                afirmaQue_ProdutoDto_Response(atual)
                        .foiConvertidoDe_Produto();
            }
        }
    }
}