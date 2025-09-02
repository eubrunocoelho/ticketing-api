package com.eubrunocoelho.ticketing.sort.reply;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Locale;

public class ReplySort {

    protected static final String[] SORT = {"NEW", "OLDER", "LAST_UPDATED"};

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
            case "LAST_UPDATE" -> Sort.by(Sort.Direction.DESC, "updatedAt");

            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }
}
