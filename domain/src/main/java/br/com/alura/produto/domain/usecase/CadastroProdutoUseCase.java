package br.com.alura.produto.domain.usecase;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.domain.repository.BucketRepository;
import br.com.alura.produto.domain.repository.ProdutoRepository;
import br.com.alura.produto.domain.repository.QueueRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.alura.produto.domain.util.ValidationUtil.validate;

@RequiredArgsConstructor
@Service
@Observed
public class CadastroProdutoUseCase {

    private final ProdutoRepository produtoRepository;

    private final BucketRepository bucketRepository;

    private final QueueRepository queueRepository;

    public Produto cadastrar(Produto produto) {
        validate(produto);

        if (!produto.getFotos().isEmpty())
            produto.getFotos()
                    .forEach(bucketRepository::armazenar);

        var produtoSalvo = produtoRepository.save(produto);

        queueRepository.notificarCadastro(produtoSalvo);

        return produtoSalvo;
    }
}
