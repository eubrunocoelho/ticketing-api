package com.eubrunocoelho.ticketing.util.sort;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Locale;

public class TicketSortHelper {

    protected static final String[] SORT = {"NEW", "OLDER", "LAST_UPDATED", "NAME"};

    public static Sort getSort(String sortParam) {
        if (sortParam == null) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String value = sortParam.toUpperCase(Locale.ROOT);

        if (!Arrays.asList(SORT).contains(value)) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        return switch (value) {
            case "NEW" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "OLDER" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "LAST_UPDATED" -> Sort.by(Sort.Direction.DESC, "updatedAt");
            case "NAME" -> Sort.by(Sort.Direction.ASC, "title");

            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }
}
