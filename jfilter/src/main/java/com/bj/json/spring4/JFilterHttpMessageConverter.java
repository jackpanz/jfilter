package com.bj.json.spring4;

import com.bj.json.BeanJFilter;
import com.bj.json.JFilterValue;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ClassUtils;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.reflect.Type;

//Spring 4.3.x+ 4.2.x+

public class JFilterHttpMessageConverter extends MappingJackson2HttpMessageConverter {



    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        if (object instanceof JFilterValue) {

            ObjectMapper objectMapper = this.objectMapper.copy();
            BeanJFilter.setJFilter((JFilterValue) object, objectMapper);

            JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
            JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
            try {
                writePrefix(generator, object);

                Class<?> serializationView = null;
                FilterProvider filters = null;
                Object value = object;
                JavaType javaType = null;
                if (object instanceof MappingJacksonValue) {
                    MappingJacksonValue container = (MappingJacksonValue) object;
                    value = container.getValue();
                    serializationView = container.getSerializationView();
                    filters = container.getFilters();
                }
                if (type != null && value != null && TypeUtils.isAssignable(type, value.getClass())) {
                    javaType = getJavaType(type, null);
                }
                ObjectWriter objectWriter;
                if (serializationView != null) {
                    objectWriter = objectMapper.writerWithView(serializationView);
                } else if (filters != null) {
                    objectWriter = objectMapper.writer(filters);
                } else {
                    objectWriter = objectMapper.writer();
                }
                if (javaType != null && javaType.isContainerType()) {
                    objectWriter = objectWriter.forType(javaType);
                }
                objectWriter.writeValue(generator, value);

                writeSuffix(generator, object);
                generator.flush();

            } catch (JsonProcessingException ex) {
                throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
            }
        }else {
            super.writeInternal(object, type, outputMessage);
        }
    }

}
