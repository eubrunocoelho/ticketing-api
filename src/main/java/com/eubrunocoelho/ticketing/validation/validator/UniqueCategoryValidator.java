package com.eubrunocoelho.ticketing.validation.validator;

import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.validation.annotation.UniqueCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String>
{
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid( String name, ConstraintValidatorContext context )
    {
        return !categoryRepository.existsByName( name );
    }
}
