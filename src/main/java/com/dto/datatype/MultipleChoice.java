package com.dto.datatype;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class MultipleChoice {
    List<Choice> choiceList;

}
