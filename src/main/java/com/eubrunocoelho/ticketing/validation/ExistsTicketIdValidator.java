package com.eubrunocoelho.ticketing.validation;

import com.eubrunocoelho.ticketing.annotation.validation.ExistsTicketId;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsTicketIdValidator implements ConstraintValidator<ExistsTicketId, Long> {

    private final TicketRepository ticketRepository;

    @Override
    public boolean isValid(Long ticketId, ConstraintValidatorContext context) {
        return ticketRepository.existsById(ticketId);
    }
}
