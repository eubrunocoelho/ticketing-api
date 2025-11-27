package com.eubrunocoelho.ticketing.service.reply.validation.update.rule;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.exception.business.ReplyAlreadyHasChildException;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.service.reply.validation.update.ReplyUpdateValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyHasNoChildrenUpdateRule implements ReplyUpdateValidationRule
{
    private final ReplyRepository replyRepository;

    @Override
    public void validate( Reply reply )
    {
        boolean isAnswered = replyRepository.existsByParentId( reply.getId() );

        if ( isAnswered )
        {
            throw new ReplyAlreadyHasChildException(
                    "A resposta já foi respondida e não pode ser atualizada."
            );
        }
    }
}
