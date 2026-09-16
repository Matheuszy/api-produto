package br.com.alura.produto.domain.entity.factory;

import br.com.alura.produto.domain.entity.Foto;
import lombok.RequiredArgsConstructor;

import static br.com.alura.produto.domain.util.DateUtil.newDateTime;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class FotoFactory {

    private final Foto.FotoBuilder builder;

    public static FotoFactory criarFoto() {
        return new FotoFactory(Foto.builder());
    }

    public Foto comTodosOsCampos() {
        var result = builder
                .fotoId(1L)
                .fileName("file-name-1.jpg")
                .link("https://example.com/foto1.jpg")
                .base64("Y29udGVudC0x")
                .criadoEm(newDateTime("13/12/2025 23:59:59"))
                .atualizadoEm(newDateTime("14/12/2025 23:59:59"))
                .build();
        // E
        afirmaQueOObjeto(result)
                .naoTemCamposVazios();
        return result;
    }

    public Foto comTodosOsCamposExcetoDB() {
        comTodosOsCampos();
        return builder
                .fotoId(null)
                .criadoEm(null)
                .atualizadoEm(null)
                .build();
    }
}