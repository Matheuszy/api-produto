package br.com.alura.produto.application.v1.dto.assertions;

import br.com.alura.produto.application.v1.dto.ProdutoDto;
import br.com.alura.produto.domain.entity.factory.ProdutoFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static br.com.alura.produto.application.v1.dto.assertions.FotoDtoAssertions.afirmaQue_FotoDto_Response;
import static br.com.alura.produto.domain.entity.Produto.Status.AVAILABLE;
import static br.com.alura.produto.domain.util.DateUtil.newDateTime;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDtoAssertions {

    public static Response afirmaQue_ProdutoDto_Response(ProdutoDto.Response atual) {
        return new Response(spy(atual));
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static class Response {

        private final ProdutoDto.Response atual;

        /**
         * @see ProdutoFactory
         * .comTodosOsCampos()
         */
        public void foiConvertidoDe_Produto() {
            assertThat(atual.getProdutoId())
                    .hasToString("45bd68cf-f261-4187-ad59-8e8f9cce47e6");
            assertThat(atual.getNome())
                    .isEqualTo("Produto 1");
            assertThat(atual.getCategoria())
                    .isEqualTo("Categoria 1");
            assertThat(atual.getStatus())
                    .isEqualTo(AVAILABLE);
            assertThat(atual.getDescricao())
                    .isEqualTo("Descrição 1");
            assertThat(atual.getValor())
                    .isEqualTo(new BigDecimal("1.99"));
            afirmaQue_FotoDto_Response(atual.getFotos().getFirst())
                    .foiConvertidoDe_Foto();
            assertThat(atual.getTags().getFirst())
                    .isEqualTo("Tag 1");
            assertThat(atual.getCriadoEm())
                    .isEqualTo(newDateTime("21/12/2025 23:59:59"));
            assertThat(atual.getAtualizadoEm())
                    .isEqualTo(newDateTime("22/12/2025 23:59:59"));
            // E
            afirmaQueOObjeto(atual)
                    .teveTodosOsMetodosGetVerificadosPeloMenosUmaVez();
        }
    }
}