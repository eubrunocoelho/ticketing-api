package com.eubrunocoelho.ticketing.repository.specification;

import com.eubrunocoelho.ticketing.dto.ticket.TicketByUserFilterDto;
import com.eubrunocoelho.ticketing.dto.ticket.TicketFilterDto;
import com.eubrunocoelho.ticketing.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TicketSpecificationBuilder
{
    public Specification<Ticket> build( TicketFilterDto filter )
    {
        return Stream
                .of(
                        createSpecification( filter.status(), this::statusEquals ),
                        createSpecification( filter.search(), this::titleContains ),
                        createSpecification( filter.category(), this::categoryEquals ),
                        createSpecification( filter.user(), this::userIs )
                )
                .flatMap( Optional::stream )
                .reduce( Specification::and )
                .orElse(
                        ( root, query, cb ) -> cb.conjunction()
                );
    }

    public Specification<Ticket> build( TicketByUserFilterDto filter )
    {
        return build(
                new TicketFilterDto(
                        filter.status(),
                        filter.search(),
                        filter.category(),
                        null
                )
        );
    }

    private <V> Optional<Specification<Ticket>> createSpecification(
            V value,
            Function<V, Specification<Ticket>> function
    )
    {
        if ( value instanceof String stringValue )
        {
            return StringUtils.hasText( stringValue )
                    ? Optional.of( function.apply( value ) )
                    : Optional.empty();
        }

        return Optional.ofNullable( value ).map( function );
    }

    private Specification<Ticket> statusEquals( String status )
    {
        return ( root, query, cb ) -> cb.equal( root.get( "status" ), status );
    }

    private Specification<Ticket> titleContains( String searchTerm )
    {
        return ( root, query, cb ) ->
                cb.like( cb.lower( root.get( "title" ) ), "%" + searchTerm.toLowerCase() + "%" );
    }

    private Specification<Ticket> categoryEquals( Long categoryId )
    {
        return ( root, query, cb ) ->
                cb.equal( root.get( "category" ).get( "id" ), categoryId );
    }

    private Specification<Ticket> userIs( String userValue )
    {
        return ( root, query, cb ) ->
                cb.or(
                        cb.equal( cb.lower( root.get( "user" ).get( "username" ) ), userValue.toLowerCase() ),
                        cb.equal( cb.lower( root.get( "user" ).get( "email" ) ), userValue.toLowerCase() )
                );
    }
}
