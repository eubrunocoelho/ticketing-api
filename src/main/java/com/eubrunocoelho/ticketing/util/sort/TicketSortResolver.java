package com.eubrunocoelho.ticketing.util.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TicketSortResolver extends SortResolver<TicketSortProperty> {

    public TicketSortResolver() {
        super(TicketSortProperty.class, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
