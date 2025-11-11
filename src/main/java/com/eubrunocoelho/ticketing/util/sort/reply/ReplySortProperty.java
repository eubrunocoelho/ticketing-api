package com.eubrunocoelho.ticketing.util.sort.reply;

import com.eubrunocoelho.ticketing.util.sort.core.SortableProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReplySortProperty implements SortableProperty
{
    /**
     * Ordenar por novas respostas
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    NEW( Sort.by( Sort.Direction.DESC, "createdAt" ) ),

    /**
     * Ordenar por respostas antigas
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    OLDER( Sort.by( Sort.Direction.ASC, "createdAt" ) ),

    /**
     * Ordenar por respostas recém-atualizadas
     */
    @SuppressWarnings( "checkstyle:UnusedVariable" )
    LAST_UPDATE( Sort.by( Sort.Direction.DESC, "updatedAt" ) );

    private final Sort sort;
}
