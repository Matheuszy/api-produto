package br.com.alura.produto.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan("br.com.alura.produto.domain.entity")
@EnableJpaRepositories("br.com.alura.produto.infra.repository")
public class JpaConfig {

}
