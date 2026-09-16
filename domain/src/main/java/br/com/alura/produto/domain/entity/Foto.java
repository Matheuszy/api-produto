package br.com.alura.produto.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.net.URL;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@Builder
@EqualsAndHashCode
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Foto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long fotoId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String link;

    @Transient
    private String base64;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    @Column
    private LocalDateTime atualizadoEm;

    public void atualizar(URL url) {
        this.link = url.toString();
    }

    public void atualizar(Foto foto) {
        if (foto == null)
            return;

        if (foto.fileName != null)
            this.fileName = foto.fileName;

        if (foto.link != null)
            this.link = foto.link;
    }
}
