package br.com.alura.produto.application.v1.dto;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.infra.def.ProdutoDef;
import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDto {

    @Getter
    @Builder
    public static class Request implements ProdutoDef.Request {

        private String nome;
        private String categoria;
        private Produto.Status status;
        private String descricao;
        @DecimalMin(value = "1.99", message = "deve ser maior ou igual a R$ 1,99")
        private BigDecimal valor;
        @Singular(value = "foto", ignoreNullCollections = true)
        private List<FotoDto.Request> fotos;
        @Singular(value = "tag", ignoreNullCollections = true)
        private List<String> tags;
    }

    @Getter
    @Builder
    public static class Response implements ProdutoDef.Response {

        private UUID produtoId;
        private String nome;
        private String categoria;
        private Produto.Status status;
        private String descricao;
        private BigDecimal valor;
        @Singular(value = "foto", ignoreNullCollections = true)
        private List<FotoDto.Response> fotos;
        @Singular(value = "tag", ignoreNullCollections = true)
        private List<String> tags;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;
    }

    @Getter
    @Builder
    public static class Representacao implements ProdutoDef.Representacao {

        private UUID produtoId;
        private String nome;
        private String categoria;
        private Produto.Status status;
        private BigDecimal valor;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;
    }
}
