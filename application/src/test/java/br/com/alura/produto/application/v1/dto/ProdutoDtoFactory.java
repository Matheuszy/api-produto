package br.com.alura.produto.application.v1.dto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static br.com.alura.produto.application.v1.dto.FotoDtoFactory.criarFotoDtoRequest;
import static br.com.alura.produto.domain.entity.Produto.Status.AVAILABLE;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDtoFactory {

    public static Request criarProdutoDtoRequest() {
        return new Request(ProdutoDto.Request.builder());
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static final class Request {

        private final ProdutoDto.Request.RequestBuilder builder;

        public ProdutoDto.Request comTodosOsCampos() {
            var result = builder
                    .nome("Produto Teste")
                    .categoria("Categoria 1")
                    .status(AVAILABLE)
                    .descricao("Descrição do Produto Teste")
                    .valor(new BigDecimal("1.99"))
                    .foto(criarFotoDtoRequest()
                            .comTodosOsCampos())
                    .tag("tag-1")
                    .build();
            // E
            afirmaQueOObjeto(result)
                    .naoTemCamposVazios();
            return result;
        }
    }
}