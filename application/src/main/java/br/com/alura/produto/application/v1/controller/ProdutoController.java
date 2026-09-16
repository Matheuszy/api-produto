package br.com.alura.produto.application.v1.controller;

import br.com.alura.produto.application.v1.dto.ProdutoDto;
import br.com.alura.produto.application.v1.mapper.ProdutoDtoMapper;
import br.com.alura.produto.domain.usecase.CadastroProdutoUseCase;
import br.com.alura.produto.domain.usecase.ConsultaProdutoUseCase;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.mapstruct.factory.Mappers.getMapper;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/produtos")
@Observed
public class ProdutoController {

    public static final ProdutoDtoMapper mapper = getMapper(ProdutoDtoMapper.class);

    private final CadastroProdutoUseCase cadastroCarrinhoUseCase;

    private final ConsultaProdutoUseCase consultaProdutoUseCase;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ProdutoDto.Response cadastrarProduto(
            @Valid
            @RequestBody
            ProdutoDto.Request requestBody) {
        var produto = mapper.converter(requestBody);
        var produtoCriado = cadastroCarrinhoUseCase.cadastrar(produto);
        return mapper.converter(produtoCriado);
    }

    @GetMapping(path = "/{produtoId}",
            produces = APPLICATION_JSON_VALUE)
    @Cacheable(value = "Produtos", key = "#produtoId")
    public ProdutoDto.Response consultarProdutoPorId(
            @PathVariable("produtoId")
            UUID produtoId) {
        var produtoCarregado = consultaProdutoUseCase.consultarPorId(produtoId);
        return mapper.converter(produtoCarregado);
    }
}
