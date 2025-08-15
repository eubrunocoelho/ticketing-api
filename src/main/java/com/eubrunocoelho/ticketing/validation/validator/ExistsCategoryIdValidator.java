package com.eubrunocoelho.ticketing.validation.validator;

import com.eubrunocoelho.ticketing.validation.annotation.ExistsCategoryId;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsCategoryIdValidator implements ConstraintValidator<ExistsCategoryId, Long> {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext context) {
        return categoryRepository.existsById(categoryId);
    }
}
