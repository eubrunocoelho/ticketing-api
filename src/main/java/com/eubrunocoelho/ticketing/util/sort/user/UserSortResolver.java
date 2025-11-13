package com.eubrunocoelho.ticketing.util.sort.user;

import com.eubrunocoelho.ticketing.util.sort.core.SortResolver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class UserSortResolver extends SortResolver<UserSortProperty>
{
    public UserSortResolver()
    {
        super(
                UserSortProperty.class,
                Sort.by( Sort.Direction.DESC, "createdAt" )
        );
    }
}
