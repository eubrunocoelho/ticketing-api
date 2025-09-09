package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
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
        config = CentralMapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    @Named("categoryToEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriority")
    Category toEntity(CategoryCreateDto dto);

    @Named("categoryToDto")
    CategoryResponseDto toDto(Category entity);

    @Named("updateCategoryFromDto")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapPriority")
    void updateCategoryFromDto(CategoryUpdateDto categoryUpdateDto, @MappingTarget Category category);

    @Named("mapCategoryForTicket")
    @Mapping(target = "id", expression = "java(null)")
    CategoryResponseDto mapCategoryForTicket(Category category);

    @Named("mapPriority")
    default Category.Priority mapPriority(String priority) {
        return getEnumValueOrThrow(priority, Category.Priority.class);
    }
}
