package com.factory;

import com.dto.datatype.*;
import com.type.ColumnType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataFactory {
    private static final Logger LOGGER = Logger.getLogger(DataFactory.class.getName());

    public Object createData(String data, ColumnType type) {
        if (data.isEmpty()) {
            return null;
        }
        return switch (type) {
            case TEXT -> new Text(data);
            case DATETIME -> convertDate(data);
            case NUMBER -> convertNumber(data);
            case YESNO -> convertYesNo(data);
            case MULTIPLE_CHOICE -> convertMultipleChoice(data);
            case CHOICE -> convertChoice(data);
            case HYPERLINK -> convertHyperLink(data);
            case IMAGE -> convertImage(data);
            case PERSON -> convertPerson(data);
            case MULTIPLE_PERSON -> convertMultiplePerson(data);
            default -> throw new IllegalArgumentException("Unsupported ColumnType: " + type);
        };
    }

    public MultiplePerson convertMultiplePerson(String data) {
        String[] people = data.split("#");
        return new MultiplePerson(Arrays.stream(people).map(this::convertPerson).toList());
    }

    public Person convertPerson(String data) {
        String[] arr = data.split(",");
        return new Person(arr[0], arr[1]);
    }

    public Image convertImage(String data) {
        return new Image(data);
    }

    public HyperLink convertHyperLink(String data) {
        String[] arr = data.split(",");
        return new HyperLink(arr[0], arr[1]);
    }

    public com.dto.datatype.Number convertNumber(String data) {
        return new com.dto.datatype.Number(Double.parseDouble(data));
    }

    public DateTime convertDate(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(data);
            return new DateTime(date);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Invalid date format: {}", data);
            return null;
        }
    }

    public Choice convertChoice(String data) {
        String[] arr = data.split(",");
        return new Choice(arr[0], arr[1]);
    }

    public YesNo convertYesNo(String data) {
        return new YesNo(Boolean.parseBoolean(data));
    }

    public MultipleChoice convertMultipleChoice(String data) {
        String[] choices = data.split("#");
        return new MultipleChoice(Arrays.stream(choices).map(this::convertChoice).toList());
    }

}
