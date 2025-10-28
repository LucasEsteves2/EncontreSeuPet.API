package edu.infnet.lucasapi.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull cuida disso

        boolean valido = Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value.toString()));

        if (!valido) {
            // Monta a lista de valores válidos dinamicamente
            String valoresValidos = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    String.format("Valor inválido. Use um dos seguintes: [%s]", valoresValidos)
            ).addConstraintViolation();
        }

        return valido;
    }
}