package com.eubrunocoelho.ticketing.validation.validator;

import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.validation.annotation.UniqueForUpdateCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueForUpdateCategoryValidator implements
        ConstraintValidator<UniqueForUpdateCategory, CategoryUpdateDto>
{
    private final CategoryRepository categoryRepository;
    private String message;

    @Override
    public void initialize( UniqueForUpdateCategory constraintAnnotation )
    {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid( CategoryUpdateDto categoryUpdateDto, ConstraintValidatorContext context )
    {
        if ( categoryUpdateDto.name() == null )
        {
            return true;
        }

        boolean exists = categoryRepository
                .existsByNameAndIdNot(
                        categoryUpdateDto.name(),
                        categoryUpdateDto.id()
                );

        if ( exists )
        {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate( message )
                    .addPropertyNode( "name" )
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
