package com.eubrunocoelho.ticketing.filter.ticket;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.filter.core.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class TicketFilter extends Filter<Ticket> {

    public static final String[] STATUS = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"};

    public TicketFilter(HttpServletRequest request) {
        super(request);
    }

    public Specification<Ticket> status(String value) {
        return (
                Arrays.asList(STATUS).contains(value)
        ) ?
                (root, query, cb) ->
                        cb.equal(
                                root.get("status"),
                                value
                        )
                : null;
    }

    public Specification<Ticket> search(String value) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),
                        "%" + value.toLowerCase() + "%"
                );
    }

    // Retornar null caso categoria não exista
    public Specification<Ticket> category(String value) {
        Long categoryId = Long.valueOf(value);

        return (root, query, cb) ->
                cb.equal(
                        root.get("category").get("id"),
                        categoryId
                );
    }
}
