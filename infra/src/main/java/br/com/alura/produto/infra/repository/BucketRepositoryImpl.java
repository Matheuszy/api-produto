package br.com.alura.produto.infra.repository;

import br.com.alura.produto.domain.entity.Foto;
import br.com.alura.produto.domain.repository.BucketRepository;
import io.awspring.cloud.s3.S3Template;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static java.lang.String.format;
import static java.util.Base64.getDecoder;

@Primary
@Repository
@RequiredArgsConstructor
@Observed
public class BucketRepositoryImpl implements BucketRepository {

    private final S3Template s3Template;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public Foto armazenar(Foto foto) {
        var byteArray = getDecoder()
                .decode(foto.getBase64());
        var inputStream = new ByteArrayInputStream(byteArray);
        var fileName = format("foto-produto/%s/%s", LocalDate.now(), foto.getFileName());

        if (!s3Template.bucketExists(bucketName))
            s3Template.createBucket(bucketName);

        var s3Resource = s3Template.upload(bucketName, fileName, inputStream);
        try {
            foto.atualizar(s3Resource.getURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return foto;
    }
}
