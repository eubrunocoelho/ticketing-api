package com.eubrunocoelho.ticketing.mapper;

import com.eubrunocoelho.ticketing.config.CentralMapperConfig;
import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(
        config = CentralMapperConfig.class
)
public interface MetaMapper {

    @Named("metaToDto")
    @Mapping(target = "firstPage", expression = "java(page.isFirst())")
    @Mapping(target = "lastPage", expression = "java(page.isLast())")
    @Mapping(target = "numberOfElements", expression = "java(page.getNumberOfElements())")
    @Mapping(target = "pageNumber", expression = "java(page.getNumber())")
    @Mapping(target = "pageSize", expression = "java(page.getSize())")
    @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
    MetaResponseDto toDto(Page<?> page);
}
