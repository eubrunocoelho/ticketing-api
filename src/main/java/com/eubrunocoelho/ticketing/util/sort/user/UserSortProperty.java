package com.eubrunocoelho.ticketing.util.sort.user;

import com.eubrunocoelho.ticketing.util.sort.core.SortableProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum UserSortProperty implements SortableProperty
{
    /**
     * Ordenar por novos usuários
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    NEW( Sort.by( Sort.Direction.DESC, "createdAt" ) ),

    /**
     * Ordenar por usuários antigos
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    OLDER( Sort.by( Sort.Direction.ASC, "createdAt" ) );

    private final Sort sort;
}
