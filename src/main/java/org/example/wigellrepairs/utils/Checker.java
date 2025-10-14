package org.example.wigellrepairs.utils;

import java.util.Arrays;

import org.example.wigellrepairs.enums.Expertise;
import org.example.wigellrepairs.exceptions.ResourceMissingDataException;
import org.example.wigellrepairs.exceptions.ResourceWithValueNotFoundException;
import org.example.wigellrepairs.exceptions.ValueOutOfRangeException;

public class Checker {

    public static boolean validEnum(String value) {
        return Arrays.stream(Expertise.values())
            .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }

    public static <T> void valueCheck(T value, String field){
        if(value == null)
            throw new ResourceMissingDataException(field);
        if(value instanceof String){
            String str = ((String)value).trim();
            if(str.isEmpty())
                throw new ResourceMissingDataException(field);
        }
        if(value instanceof Expertise) {
            if(!validEnum(((Expertise)value).name()))
                throw new ResourceWithValueNotFoundException("Category", value);
        }
    }

    public static void lengthCheck(String value, int max, String field){
        if(value.length() > max)
            throw new ValueOutOfRangeException(field, max);
    }
}
