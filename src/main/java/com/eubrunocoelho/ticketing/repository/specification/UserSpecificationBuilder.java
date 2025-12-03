package com.eubrunocoelho.ticketing.repository.specification;

import com.eubrunocoelho.ticketing.dto.user.UserFilterDto;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserSpecificationBuilder
{
    public Specification<User> build( UserFilterDto filter )
    {
        return Stream
                .of(
                        createSpecification( filter.search(), this::userContains ),
                        createSpecification( filter.role(), this::roleEquals ),
                        createSpecification( filter.status(), this::statusEquals )
                )
                .flatMap( Optional::stream )
                .reduce( Specification::and )
                .orElse(
                        ( root, query, cb ) -> cb.conjunction()
                );
    }

    private <V> Optional<Specification<User>> createSpecification(
            V value,
            Function<V, Specification<User>> function
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

    private Specification<User> userContains( String searchTerm )
    {
        return ( root, query, cb ) ->
                cb.or(
                        cb.like( cb.lower( root.get( "email" ) ), "%" + searchTerm.toLowerCase() + "%" ),
                        cb.like( cb.lower( root.get( "username" ) ), "%" + searchTerm.toLowerCase() + "%" )
                );
    }

    private Specification<User> roleEquals( String role )
    {
        return ( root, query, cb ) -> cb.equal( root.get( "role" ), role );
    }

    private Specification<User> statusEquals( String status )
    {
        return ( root, query, cb ) -> cb.equal( root.get( "status" ), status );
    }
}
