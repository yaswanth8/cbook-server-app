package com.careerit.cbook.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ConvertorUtil {

    public static <T> T dtoToDomain(Object dto, Class<T> clazz) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(dto, clazz);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static <T> T domainToDto(Object domain, Class<T> clazz) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(domain, clazz);
    }

}
