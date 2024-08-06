package com.dto.datatype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    String name;
    byte[] avatar;
}
