package com.eubrunocoelho.ticketing.event.reply;

import com.eubrunocoelho.ticketing.entity.Reply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReplyCreatedEvent extends ApplicationEvent
{
    private final Reply reply;

    public ReplyCreatedEvent( Object source, Reply reply )
    {
        super( source );

        this.reply = reply;
    }
}
