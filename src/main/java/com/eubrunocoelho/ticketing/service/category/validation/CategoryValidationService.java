package com.eubrunocoelho.ticketing.service.category.validation;

import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryValidationService
{
    private final Validator validator;

    public void updateValidate( CategoryUpdateDto categoryUpdateDto )
    {
        Set<ConstraintViolation<CategoryUpdateDto>> violations = validator
                .validate( categoryUpdateDto );

        if ( !violations.isEmpty() )
        {
            throw new ConstraintViolationException( violations );
        }
    }
}
