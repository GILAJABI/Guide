package com.guide.ex.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return year != null ? year.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer value) {
        return value != null ? Year.of(value) : null;
    }
}
