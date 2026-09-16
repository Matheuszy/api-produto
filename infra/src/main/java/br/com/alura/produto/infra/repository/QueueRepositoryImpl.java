package br.com.alura.produto.infra.repository;

import br.com.alura.produto.domain.entity.Produto;
import br.com.alura.produto.domain.repository.QueueRepository;
import br.com.alura.produto.infra.mapper.ProdutoMsgMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static org.mapstruct.factory.Mappers.getMapper;

@RequiredArgsConstructor
@Repository
@Primary
@Observed
public class QueueRepositoryImpl implements QueueRepository {

    private final ProdutoMsgMapper mapper = getMapper(ProdutoMsgMapper.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${message.market-place.queue-exchange}")
    public String queueExchange;

    @Value("${message.cadastro-produto.routing-key}")
    public String routingKeyName;

    @Override
    public void notificarCadastro(Produto produto) {
        var msg = mapper.converter(produto);
        rabbitTemplate.convertAndSend(queueExchange, routingKeyName, msg);
    }
}
