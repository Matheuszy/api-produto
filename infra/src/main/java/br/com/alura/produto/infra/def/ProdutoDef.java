package br.com.alura.produto.infra.def;

import br.com.alura.produto.domain.entity.Produto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ProdutoDef {

    interface Representado extends Serializable {

        String getNome();

        String getCategoria();

        Produto.Status getStatus();

        BigDecimal getValor();
    }

    interface Detalhado extends Serializable, Representado {

        String getDescricao();

        List<String> getTags();
    }

    interface RepresentadoPersistido extends Serializable, Representado {

        UUID getProdutoId();

        LocalDateTime getCriadoEm();

        LocalDateTime getAtualizadoEm();
    }

    interface DetalhadoPersistido extends Serializable, RepresentadoPersistido {

    }

    interface Request extends Detalhado {

        List<? extends FotoDef.Request> getFotos();
    }

    interface Response extends Detalhado, DetalhadoPersistido {

        List<? extends FotoDef.Response> getFotos();
    }

    interface Representacao extends RepresentadoPersistido {

    }
}
