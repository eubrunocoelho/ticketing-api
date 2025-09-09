package com.eubrunocoelho.ticketing.repository.specification;

import com.eubrunocoelho.ticketing.dto.reply.ReplyFilterDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ReplySpecificationBuilder {

    public Specification<Reply> build(ReplyFilterDto filter) {
        return Stream.of(
                        createSpecification(filter.user(), this::userIs)
                ).flatMap(Optional::stream)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private <V> Optional<Specification<Reply>> createSpecification(V value, Function<V, Specification<Reply>> function) {
        if (value instanceof String stringValue) {
            return StringUtils.hasText(stringValue) ? Optional.of(function.apply(value)) : Optional.empty();
        }

        return Optional.ofNullable(value).map(function);
    }

    private Specification<Reply> userIs(String userValue) {
        return (root, query, cb) -> cb.or(
                cb.equal(cb.lower(root.get("createdUser").get("username")), userValue.toLowerCase()),
                cb.equal(cb.lower(root.get("createdUser").get("email")), userValue.toLowerCase())
        );
    }
}
