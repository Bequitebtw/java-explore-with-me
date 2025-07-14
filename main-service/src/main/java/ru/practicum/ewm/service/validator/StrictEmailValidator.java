package ru.practicum.ewm.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.ewm.service.annotation.StrictEmail;

public class StrictEmailValidator implements ConstraintValidator<StrictEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) return false;

        String[] parts = email.split("@");
        if (parts.length != 2) return false;

        String localPart = parts[0];
        String domainPart = parts[1];

        // Проверка local-part
        if (localPart.length() > 64) return false;

        // Проверка каждого label (поддомена) в domain
        String[] domainLabels = domainPart.split("\\.");
        for (String label : domainLabels) {
            if (label.length() > 63) return false;
        }

        return true;
    }
}
