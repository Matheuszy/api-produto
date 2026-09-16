package br.com.alura.produto.infra.def;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface FotoDef {

    interface Representado extends Serializable {
        String getFileName();
    }

    interface Detalhado extends Serializable, Representado {

    }

    interface RepresentadoPersistido extends Serializable, Representado {
        Long getFotoId();

        String getLink();

        LocalDateTime getCriadoEm();

        LocalDateTime getAtualizadoEm();
    }

    interface DetalhadoPersistido extends Serializable, RepresentadoPersistido {

    }

    interface Request extends Detalhado {

        String getBase64();
    }

    interface Response extends Detalhado, DetalhadoPersistido {

    }

    interface Representacao extends RepresentadoPersistido {

    }
}
