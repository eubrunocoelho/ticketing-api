package com.eubrunocoelho.ticketing.util.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ReplySortProperty implements SortableProperty {

    NEW(Sort.by(Sort.Direction.DESC, "createdAt")),
    OLDER(Sort.by(Sort.Direction.ASC, "createdAt")),
    LAST_UPDATE(Sort.by(Sort.Direction.DESC, "updatedAt"));

    private final Sort sort;
}
