package com.eubrunocoelho.ticketing.util.sort.reply;

import com.eubrunocoelho.ticketing.util.sort.core.SortResolver;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ReplySortResolver extends SortResolver<ReplySortProperty>
{
        public ReplySortResolver()
        {
            super(
                    ReplySortProperty.class,
                    Sort.by( Sort.Direction.DESC, "createdAt" )
            );
        }
}
