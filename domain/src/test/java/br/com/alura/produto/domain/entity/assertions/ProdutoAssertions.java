package br.com.alura.produto.domain.entity.assertions;

import br.com.alura.produto.domain.entity.Produto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static br.com.alura.produto.domain.entity.Produto.Status.AVAILABLE;
import static br.com.alura.produto.domain.entity.assertions.FotoAssertions.afirmaQue_Foto;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@RequiredArgsConstructor(access = PRIVATE)
public final class ProdutoAssertions {

    private final Produto atual;

    public static ProdutoAssertions afirmaQue_Produto(Produto atual) {
        return new ProdutoAssertions(spy(atual));
    }

    /**
     * @see br.com.alura.produto.application.v1.dto.ProdutoDtoFactory
     * .comTodosOsCampos()
     */
    public void foiConvertidoDe_ProdutoDto_Request() {
        assertThat(atual.getProdutoId())
                .isNull();
        assertThat(atual.getNome())
                .isEqualTo("Produto Teste");
        assertThat(atual.getCategoria())
                .isEqualTo("Categoria 1");
        assertThat(atual.getStatus())
                .isEqualTo(AVAILABLE);
        assertThat(atual.getDescricao())
                .isEqualTo("Descrição do Produto Teste");
        assertThat(atual.getValor())
                .isEqualTo(new BigDecimal("1.99"));
        afirmaQue_Foto(atual.getFotos().getFirst())
                .foiConvertidoDe_ProdutoDto_Request();
        assertThat(atual.getTags().getFirst())
                .isEqualTo("tag-1");
        assertThat(atual.getCriadoEm())
                .isNull();
        assertThat(atual.getAtualizadoEm())
                .isNull();
        // E
        afirmaQueOObjeto(atual)
                .teveTodosOsMetodosGetVerificadosPeloMenosUmaVez();
    }
}