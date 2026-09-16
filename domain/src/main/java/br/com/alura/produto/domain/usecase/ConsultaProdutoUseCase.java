package br.com.alura.produto.domain.usecase;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.domain.exception.NotFoundException;
import br.com.alura.produto.domain.repository.ProdutoRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Observed
public class ConsultaProdutoUseCase {

    private final ProdutoRepository produtoRepository;

    public Produto consultarPorId(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Produto.class));
    }
}
