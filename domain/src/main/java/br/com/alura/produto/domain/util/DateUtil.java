package br.com.alura.produto.domain.util;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class DateUtil {

    private static final DateTimeFormatter DF = ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DTF = ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDate newDate(String text) {
        return LocalDate.parse(text, DF);
    }

    public static LocalDateTime newDateTime(String text) {
        return LocalDateTime.parse(text, DTF);
    }
}
