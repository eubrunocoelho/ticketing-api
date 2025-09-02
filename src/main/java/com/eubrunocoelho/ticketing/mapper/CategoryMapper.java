package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.MapperConfiguration;
import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static com.eubrunocoelho.ticketing.util.EnumUtil.getEnumValueOrThrow;

@Mapper(
        config = MapperConfiguration.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriority")
    Category toEntity(CategoryCreateDto dto);

    @Named("categoryToDto")
    CategoryResponseDto toDto(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriority")
    void updateCategoryFromDto(CategoryUpdateDto categoryUpdateDto, @MappingTarget Category category);

    @Named("mapPriority")
    default Category.Priority mapPriority(String priority) {
        return getEnumValueOrThrow(priority, Category.Priority.class);
    }

    @Named("mapCategoryForTicket")
    default CategoryResponseDto mapCategoryForTicket(Category category) {
        return new CategoryResponseDto(
                null,
                category.getName(),
                category.getDescription(),
                category.getPriority().name()
        );
    }
}
