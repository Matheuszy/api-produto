package br.com.alura.produto.application.v1.dto.assertions;

import br.com.alura.produto.application.v1.dto.FotoDto;
import br.com.alura.produto.domain.entity.factory.FotoFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static br.com.alura.produto.domain.util.DateUtil.newDateTime;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@NoArgsConstructor(access = PRIVATE)
public final class FotoDtoAssertions {

    public static Response afirmaQue_FotoDto_Response(FotoDto.Response atual) {
        return new Response(spy(atual));
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static class Response {

        private final FotoDto.Response atual;

        /**
         * @see FotoFactory
         * .comTodosOsCampos()
         */
        public void foiConvertidoDe_Foto() {
            assertThat(atual.getFotoId())
                    .isEqualTo(1L);
            assertThat(atual.getFileName())
                    .isEqualTo("file-name-1.jpg");
            assertThat(atual.getLink())
                    .isEqualTo("https://example.com/foto1.jpg");
            assertThat(atual.getCriadoEm())
                    .isEqualTo(newDateTime("13/12/2025 23:59:59"));
            assertThat(atual.getAtualizadoEm())
                    .isEqualTo(newDateTime("14/12/2025 23:59:59"));
            // E
            afirmaQueOObjeto(atual)
                    .teveTodosOsMetodosGetVerificadosPeloMenosUmaVez();
        }
    }
}