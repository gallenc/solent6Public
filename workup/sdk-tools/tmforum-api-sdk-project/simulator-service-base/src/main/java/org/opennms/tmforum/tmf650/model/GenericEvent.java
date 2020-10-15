package org.opennms.tmforum.tmf650.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "A generic event which can be mapped to an api event")
public class GenericEvent {
    
    // see https://www.baeldung.com/jackson-mapping-dynamic-object
    Map<String, Object> details = new LinkedHashMap<>();
    
    @JsonAnySetter
    void setDetail(String key, Object value) {
        details.put(key, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenericEvent other = (GenericEvent) obj;
        return Objects.equals(details, other.details);
    }

    @Override
    public String toString() {
        return "GenericEvent [details=" + details + "]";
    }
    
    
    

}
