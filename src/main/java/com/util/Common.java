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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiConsumer;

public class Common {
    private Common() {
    }

    public static void validateConfig(ColumnConfig config, String data) {
        ConfigType configType = config.getConfigType();
        String configValue = config.getConfigValue();
        switch (configType) {
            case MAX_CHARACTER:
                validateMaxCharacter(data, configValue);
                break;
            case REQUIRE:
                validateRequirement(data);
                break;
            case MIN_VALUE:
                validateMinValue(data, configValue);
                break;
            case MAX_VALUE:
                validateMaxValue(data, configValue);
                break;
            case DEFAULT_VALUE:
                break;
            default:
                throw new IllegalArgumentException("Unknown ConfigType: " + configType);
        }
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

    public static int parseInt(String value) {
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

    public static String encodeImageToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
