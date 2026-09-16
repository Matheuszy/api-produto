package br.com.alura.produto.infra.msg;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.infra.def.ProdutoDef;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ProdutoMsg implements ProdutoDef.Response {

    private UUID produtoId;
    private String nome;
    private String categoria;
    private Produto.Status status;
    private String descricao;
    private BigDecimal valor;
    @Singular(value = "foto", ignoreNullCollections = true)
    private List<FotoMsg> fotos;
    @Singular(value = "tag", ignoreNullCollections = true)
    private List<String> tags;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
