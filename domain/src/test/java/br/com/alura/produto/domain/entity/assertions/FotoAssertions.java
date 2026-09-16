package br.com.alura.produto.domain.entity.assertions;

import br.com.alura.produto.domain.entity.Foto;
import lombok.RequiredArgsConstructor;

import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@RequiredArgsConstructor(access = PRIVATE)
public final class FotoAssertions {

    private final Foto atual;

    public static FotoAssertions afirmaQue_Foto(Foto atual) {
        return new FotoAssertions(spy(atual));
    }

    /**
     * @see br.com.alura.produto.application.v1.dto.FotoDtoFactory
     * .comTodosOsCampos()
     */
    public void foiConvertidoDe_ProdutoDto_Request() {
        assertThat(atual.getFotoId())
                .isNull();
        assertThat(atual.getFileName())
                .isEqualTo("file-name-1.jpg");
        assertThat(atual.getLink())
                .isNull();
        assertThat(atual.getBase64())
                .isEqualTo("Y29udGVudC0x");
        assertThat(atual.getCriadoEm())
                .isNull();
        assertThat(atual.getAtualizadoEm())
                .isNull();
        // E
        afirmaQueOObjeto(atual)
                .teveTodosOsMetodosGetVerificadosPeloMenosUmaVez();
    }
}