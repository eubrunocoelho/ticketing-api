package com.eubrunocoelho.ticketing.util.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ReplySortResolver extends SortResolver<ReplySortProperty> {

        public ReplySortResolver() {
            super(ReplySortProperty.class, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
}
