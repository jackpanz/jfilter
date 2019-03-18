package com.github.jackpanz.json;

import com.github.jackpanz.json.annotation.JFilter;
import com.github.jackpanz.json.annotation.JFilters;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@ControllerAdvice
public class JFilterResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && (returnType.getMethodAnnotation(JFilters.class) != null || returnType.getMethodAnnotation(JFilter.class) != null);
    }

    @Override
    protected MappingJacksonValue getOrCreateContainer(Object body) {
        return (body instanceof MappingJacksonValue ? (MappingJacksonValue) body : new JFilterValue(body));
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request,
                                           ServerHttpResponse response) {

        JFilters jFilters = returnType.getMethodAnnotation(JFilters.class);
        if (jFilters != null) {
            ((JFilterValue) bodyContainer).setJfilters(returnType.getMethodAnnotation(JFilters.class).value());
        } else {
            JFilter jFilter = returnType.getMethodAnnotation(JFilter.class);
            if (jFilter != null) {
                ((JFilterValue) bodyContainer).setJfilters(new JFilter[]{returnType.getMethodAnnotation(JFilter.class)});
            }
        }
    }

}