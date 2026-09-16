package br.com.alura.produto.domain.entity.factory;

import br.com.alura.produto.domain.entity.Produto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static br.com.alura.produto.domain.entity.Produto.Status.AVAILABLE;
import static br.com.alura.produto.domain.entity.factory.FotoFactory.criarFoto;
import static br.com.alura.produto.domain.util.DateUtil.newDateTime;
import static br.com.alura.produto.domain.util.ReflectionUtil.afirmaQueOObjeto;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class ProdutoFactory {

    private final Produto.ProdutoBuilder builder;

    public static ProdutoFactory criarProduto() {
        return new ProdutoFactory(Produto.builder());
    }

    public Produto comTodosOsCampos() {
        var result = builder
                .produtoId(UUID.fromString("45bd68cf-f261-4187-ad59-8e8f9cce47e6"))
                .nome("Produto 1")
                .categoria("Categoria 1")
                .tag("Tag 1")
                .status(AVAILABLE)
                .descricao("Descrição 1")
                .valor(new BigDecimal("1.99"))
                .foto(criarFoto()
                        .comTodosOsCampos())
                .criadoEm(newDateTime("21/12/2025 23:59:59"))
                .atualizadoEm(newDateTime("22/12/2025 23:59:59"))
                .build();
        // E
        afirmaQueOObjeto(result)
                .naoTemCamposVazios();
        return result;
    }

    public Produto comTodosOsCamposExcetoDB() {
        comTodosOsCampos();
        return builder
                .produtoId(null)
                .criadoEm(null)
                .atualizadoEm(null)
                .clearFotos()
                .foto(criarFoto()
                        .comTodosOsCamposExcetoDB())
                .build();
    }
}
