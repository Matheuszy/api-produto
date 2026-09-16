package br.com.alura.produto.domain.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@Slf4j
@RequiredArgsConstructor(access = PRIVATE)
public final class ReflectionUtil {

    private final Object actual;

    public static ReflectionUtil afirmaQueOObjeto(Object actual) {
        return new ReflectionUtil(actual);
    }

    public void temOsMesmosCamposQue(Object esperado, String... exceto) {
        try {
            var fields = actual.getClass().getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                var actualFieldValue = field.get(actual);

                var expectedField = esperado.getClass()
                        .getDeclaredField(field.getName());
                expectedField.setAccessible(true);

                var expectedFieldValue = expectedField.get(esperado);

                if (stream(exceto).toList().contains(field.getName()))
                    assertThat(actualFieldValue)
                            .as(format("Expected that [actual.%s] was not equal to [esperado.%s], but it was.", field.getName(), expectedField.getName()))
                            .isNotEqualTo(expectedFieldValue);
                else
                    assertThat(actualFieldValue)
                            .as(format("Expected that [actual.%s] was equal to [esperado.%s], but it wasn't.", field.getName(), expectedField.getName()))
                            .isEqualTo(expectedFieldValue);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void naoTemCamposVazios() {
        naoTemCamposVazios(actual.getClass());
    }

    private void naoTemCamposVazios(Class<?> clazz) {
        try {
            var fields = clazz.getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);

                var actualFieldValue = field.get(actual);
                if (actualFieldValue instanceof Collection<?> collection)
                    assertThat(collection)
                            .as(format("Expected that [actual.%s] wasn't null or empty, but it was.", field.getName()))
                            .isNotEmpty();
                else
                    assertThat(actualFieldValue)
                            .as(format("Expected that [actual.%s] wasn't null, but it was.", field.getName()))
                            .isNotNull();
            }

            var superClass = clazz.getSuperclass();
            if (superClass != Object.class)
                naoTemCamposVazios(superClass);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void temTodosOsCamposVazios(String... exceto) {
        try {
            var fields = actual.getClass().getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true); //NOSONAR

                var actualFieldValue = field.get(actual);
                if (!stream(exceto).toList().contains(field.getName())) {
                    if (actualFieldValue instanceof Collection<?> collection)
                        assertThat(collection)
                                .as(format("Expected that [actual.%s] was null or empty, but it wasn't.", field.getName()))
                                .isNullOrEmpty();
                    else
                        assertThat(actualFieldValue)
                                .as(format("Expected that [actual.%s] was null, but it wasn't.", field.getName()))
                                .isNull();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void teveTodosOsMetodosGetVerificadosPeloMenosUmaVez() {
        teveTodosOsMetodosGetVerificadosPeloMenosUmaVez(actual.getClass());
    }

    private void teveTodosOsMetodosGetVerificadosPeloMenosUmaVez(Class<?> clazz) {
        var methods = clazz.getDeclaredMethods();
        try {
            for (var method : methods) {
                if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                    var verify = verify(actual, atLeastOnce());
                    method.invoke(verify);
                }
            }
            var superClass = clazz.getSuperclass();
            if (superClass != Object.class)
                teveTodosOsMetodosGetVerificadosPeloMenosUmaVez(superClass);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
