package com.eubrunocoelho.ticketing.util.sort.ticket;

import com.eubrunocoelho.ticketing.util.sort.core.SortableProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum TicketSortProperty implements SortableProperty
{
    /**
     * Ordenar por novos tickets
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    NEW( Sort.by( Sort.Direction.DESC, "createdAt" ) ),

    /**
     * Ordenar por tickets antigos
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    OLDER( Sort.by( Sort.Direction.ASC, "createdAt" ) ),

    /**
     * Ordenar por tickets recém-atualizados
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    LAST_UPDATE( Sort.by( Sort.Direction.DESC, "updatedAt" ) ),

    /**
     * Ordenar por tickets de a-Z
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    NAME( Sort.by( Sort.Direction.ASC, "title" ) );

    private final Sort sort;
}
