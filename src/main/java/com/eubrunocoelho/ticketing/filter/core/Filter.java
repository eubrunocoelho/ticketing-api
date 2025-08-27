package com.eubrunocoelho.ticketing.filter.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public abstract class Filter<T> {

    protected final HttpServletRequest request;

    protected Filter(HttpServletRequest request) {
        this.request = request;
    }

    public Specification<T> toSpecification() {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            for (Map.Entry<String, String[]> entry: request.getParameterMap().entrySet()) {
                String paramName = entry.getKey();
                String[] values = entry.getValue();

                try {
                    Method method = Arrays.stream(this.getClass().getMethods())
                            .filter(m -> m.getName().equals(paramName))
                            .findFirst()
                            .orElse(null);

                    if (method != null) {
                        Object result;

                        if (values.length == 1) {
                            result = method.invoke(this, values[0]);
                        } else {
                            result = method.invoke(this, (Object) values);
                        }

                        if (result instanceof Specification<?> spec) {
                            predicates = cb.and(predicates, ((Specification<T>) spec).toPredicate(root, query, cb));
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("Erro aplicando filtro para parâmetro: {paramName}: " + paramName, ex);
                }
            }

            return predicates;
        };
    }
}
