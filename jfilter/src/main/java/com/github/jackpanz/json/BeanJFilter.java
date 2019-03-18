package com.github.jackpanz.json;

import com.github.jackpanz.json.annotation.JFilter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.Serializable;
import java.util.*;

@JsonFilter("BeanJFilter")
public class BeanJFilter extends SimpleBeanPropertyFilter implements Serializable {

    public Map<Class, Set<String>> includesClazz ;

    public BeanJFilter() {
        this.includesClazz = new HashMap<>();
    }

    public BeanJFilter addFilter(Class clazz, String propertyArray) {
        includesClazz.put(clazz, new HashSet<>(Arrays.asList(propertyArray.split(","))));
        return this;
    }

    protected boolean include(BeanPropertyWriter writer) {
        if( includesClazz.containsKey(writer.getMember().getDeclaringClass()) ){
            return !this.includesClazz.get(writer.getMember().getDeclaringClass()).contains(writer.getName());
        }
        return true;
    }

    protected boolean include(PropertyWriter writer) {
        if( includesClazz.containsKey(writer.getMember().getDeclaringClass()) ){
            return !this.includesClazz.get(writer.getMember().getDeclaringClass()).contains(writer.getName());
        }
        return true;
    }

    public static void setJFilter(JFilterValue jFilterValue, ObjectMapper objectMapper ){
        JFilter[] jFilter = jFilterValue.getJfilters();
        if (jFilter.length > 0) {
            BeanJFilter beanJFilter = new BeanJFilter();
            for (int i = 0; i < jFilter.length; i++) {
                beanJFilter.addFilter(jFilter[i].clazz(), jFilter[i].property());
                objectMapper.addMixIn(jFilter[i].clazz(), BeanJFilter.class);
            }
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter("BeanJFilter", beanJFilter));
        }
    }



}
