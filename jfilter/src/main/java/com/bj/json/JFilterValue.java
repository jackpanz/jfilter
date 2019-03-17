package com.bj.json;

import com.bj.json.annotation.JFilter;
import org.springframework.http.converter.json.MappingJacksonValue;

public class JFilterValue extends MappingJacksonValue {

    private JFilter[] jfilters = null;

    /**
     * Create a new instance wrapping the given POJO to be serialized.
     *
     * @param value the Object to be serialized
     */
    public JFilterValue(Object value) {
        super(value);
    }

    public JFilter[] getJfilters() {
        return jfilters;
    }

    public void setJfilters(JFilter[] jfilters) {
        this.jfilters = jfilters;
    }
}
