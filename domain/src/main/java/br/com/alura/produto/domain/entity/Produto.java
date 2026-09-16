package br.com.alura.produto.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Builder
@EqualsAndHashCode
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Produto implements Serializable {

    @Id
    @GeneratedValue
    private UUID produtoId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String categoria;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        AVAILABLE("Disponível"),
        PENDING("Pendente"),
        SOLD("Vendido");

        private final String descricao;
    }

    @Enumerated(STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Singular(value = "foto", ignoreNullCollections = true)
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "produto_id", updatable = false, nullable = false)
    private List<Foto> fotos;

    @ElementCollection(targetClass = String.class, fetch = EAGER)
    @CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "produto_id"))
    @Column(name = "tag", nullable = false)
    @Singular(value = "tag", ignoreNullCollections = true)
    private List<String> tags;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    @Column
    private LocalDateTime atualizadoEm;

    public void atualizar(Produto produto) {
        if (produto == null)
            return;

        if (produto.nome != null)
            this.nome = produto.nome;

        if (produto.categoria != null)
            this.categoria = produto.categoria;

        if (produto.tags != null)
            this.tags = produto.tags;

        if (produto.status != null)
            this.status = produto.status;

        if (produto.descricao != null) {
            var descStatus = switch (this.status) {
                case AVAILABLE -> "(Disponível)";
                case PENDING -> "(Pendente)";
                case SOLD -> "(Vendido)";
            };
            this.descricao = produto.descricao + " " + descStatus;
        }

        if (produto.valor != null)
            this.valor = produto.valor;

        atualizar(produto.getFotos());
    }

    private void atualizar(List<Foto> fotos) {
        if (fotos == null || fotos.isEmpty())
            return;

        for (var i = 0; i < fotos.size(); i++)
            this.fotos.get(i).atualizar(fotos.get(i));
    }
}
