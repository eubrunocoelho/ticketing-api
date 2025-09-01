package com.eubrunocoelho.ticketing.filter.ticket;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.filter.core.Filter;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class TicketFilter extends Filter<Ticket> {

    public static final String[] STATUS = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"};

    private final CategoryRepository categoryRepository;

    public TicketFilter(HttpServletRequest request, CategoryRepository categoryRepository) {
        super(request);

        this.categoryRepository = categoryRepository;
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

    public Specification<Ticket> category(String value) {
        Long categoryId = Long.valueOf(value);

        return (
                categoryRepository.existsById(categoryId)
        ) ?
                (root, query, cb) ->
                        cb.equal(
                                root.get("category").get("id"),
                                categoryId
                        )
                : null;
    }

    public Specification<Ticket> user(String value) {
        return (root, query, cb) ->
                cb.or(
                        cb.equal(
                                cb.lower(root.get("user").get("username")), value.toLowerCase()
                        ),
                        cb.equal(
                                cb.lower(root.get("user").get("email")), value.toLowerCase()
                        )
                );
    }
}
