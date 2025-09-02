package com.eubrunocoelho.ticketing.filter.reply;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.filter.core.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;

public class ReplyFilter extends Filter<Reply> {

    public ReplyFilter(HttpServletRequest request) {
        super(request);
    }

    public Specification<Reply> user(String value) {
        return (root, query, cb) ->
                cb.or(
                        cb.equal(
                                cb.lower(root.get("createdUser").get("username")), value.toLowerCase()
                        ),
                        cb.equal(
                                cb.lower(root.get("createdUser").get("email")), value.toLowerCase()
                        )
                );
    }
}
