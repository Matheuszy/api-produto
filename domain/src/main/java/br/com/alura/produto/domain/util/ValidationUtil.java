package br.com.alura.produto.domain.util;

import br.com.alura.produto.domain.exception.BusinessException;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class ValidationUtil {

    public static void validate(Object object) {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var violations = factory.getValidator()
                    .validate(object);

            BusinessException businessException = null;
            if (!violations.isEmpty()) {
                for (var violation : violations) {
                    var message = format("[%s] %s", violation.getPropertyPath(), violation.getMessage());
                    if (businessException == null) {
                        businessException = new BusinessException(message);
                        if (violations.size() > 1)
                            businessException.addSuppressed(new BusinessException(message));
                    } else {
                        businessException.addSuppressed(new BusinessException(message));
                    }
                }
            }
        }
    }
}
