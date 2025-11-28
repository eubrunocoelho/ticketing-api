package com.eubrunocoelho.ticketing.util;

import com.eubrunocoelho.ticketing.context.ScreenLabelRequestContext;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;
import com.eubrunocoelho.ticketing.mapper.MetaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseBuilder
{
    private final MetaMapper metaMapper;

    @Value( "${com.eubrunocoelho.ticketing.logger.print_logger}" )
    private boolean printLogger;

    private String resolveScreenLabel()
    {
        return ( printLogger )
                ? ScreenLabelRequestContext.getScreenLabel()
                : null;
    }

    public <T> ResponseDto<T> buildSingle( T data )
    {
        String screenLabel = resolveScreenLabel();

        return new ResponseDto<>( screenLabel, data, null );
    }

    public <T> ResponseDto<List<T>> buildPaged( Page<T> page )
    {
        String screenLabel = resolveScreenLabel();

        MetaResponseDto meta = metaMapper.toDto( page );

        return new ResponseDto<>( screenLabel, page.getContent(), meta );
    }
}
