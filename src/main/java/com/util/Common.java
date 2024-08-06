package com.util;

import com.dto.RowDTO;
import com.exception.ConstraintViolationException;
import com.exception.ExistException;
import com.exception.NotFoundException;
import com.model.Column;
import com.model.ColumnConfig;
import com.model.Row;
import com.model.SmartList;
import com.type.ConfigType;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Common {
    private Common() {
    }

    public static void validateMaxCharacter(String data, String configValue) {
        int maxLength = parseInt(configValue);
        if (data.length() > maxLength) {
            throw new ConstraintViolationException();
        }
    }

    public static void validateRequirement(String data) {
        if (data == null || data.isEmpty()) {
            throw new ConstraintViolationException();
        }
    }

    public static void validateMinValue(String data, String configValue) {
        double dataValue = parseDouble(data);
        double minValue = parseDouble(configValue);
        if (dataValue < minValue) {
            throw new ConstraintViolationException();
        }
    }

    public static void validateMaxValue(String data, String configValue) {
        double dataValue = parseDouble(data);
        double maxValue = parseDouble(configValue);
        if (dataValue > maxValue) {
            throw new ConstraintViolationException();
        }
    }

    public static  int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer format: " + value, e);
        }
    }

    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid double format: " + value, e);
        }
    }


    public static void checkExist(Object o) {
        if (o != null) {
            throw new ExistException();
        }
    }

    public static void checkNonExist(Object o) {
        if (o == null) {
            throw new NotFoundException();
        }
    }


}
