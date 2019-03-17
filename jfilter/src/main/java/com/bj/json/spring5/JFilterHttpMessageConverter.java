package com.bj.json.spring5;

import com.bj.json.BeanJFilter;
import com.bj.json.JFilterValue;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.lang.Nullable;
import org.springframework.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * spring 5.0+
 */
public class JFilterHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public PrettyPrinter mSsePrettyPrinter;

    public JFilterHttpMessageConverter() {
        super();
    }

    public JFilterHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\ndata:"));
        this.mSsePrettyPrinter = prettyPrinter;
    }

    @Override
    protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof JFilterValue) {

            //用单独objectMapper处理
            ObjectMapper objectMapper = this.objectMapper.copy();
            BeanJFilter.setJFilter((JFilterValue) object, objectMapper);

            //以下代码跟源代码一样
            MediaType contentType = outputMessage.getHeaders().getContentType();
            JsonEncoding encoding = getJsonEncoding(contentType);
            JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
            try {
                writePrefix(generator, object);

                Object value = object;
                Class<?> serializationView = null;
                FilterProvider filters = null;
                JavaType javaType = null;

                if (object instanceof MappingJacksonValue) {
                    MappingJacksonValue container = (MappingJacksonValue) object;
                    value = container.getValue();
                    serializationView = container.getSerializationView();
                    filters = container.getFilters();
                }
                if (type != null && TypeUtils.isAssignable(type, value.getClass())) {
                    javaType = getJavaType(type, null);
                }

                ObjectWriter objectWriter = (serializationView != null ?
                        objectMapper.writerWithView(serializationView) : objectMapper.writer());
                if (filters != null) {
                    objectWriter = objectWriter.with(filters);
                }
                if (javaType != null && javaType.isContainerType()) {
                    objectWriter = objectWriter.forType(javaType);
                }
                SerializationConfig config = objectWriter.getConfig();
                if (contentType != null && contentType.isCompatibleWith(MediaType.TEXT_EVENT_STREAM) &&
                        config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
                    objectWriter = objectWriter.with(this.mSsePrettyPrinter);
                }
                objectWriter.writeValue(generator, value);

                writeSuffix(generator, object);
                generator.flush();
            } catch (InvalidDefinitionException ex) {
                throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
            } catch (JsonProcessingException ex) {
                throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getOriginalMessage(), ex);
            }
        } else {
            super.writeInternal(object, type, outputMessage);
        }

    }


}
