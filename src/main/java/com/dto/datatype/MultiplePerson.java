package com.dto.datatype;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class MultiplePerson {
    List<Person> personList;
}
