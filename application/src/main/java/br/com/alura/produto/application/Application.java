package br.com.alura.produto.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = "br.com.alura")
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }
}
