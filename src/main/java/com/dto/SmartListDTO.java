package com.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SmartListDTO {
    String id;
    String name;
    List<RowDTO> rows;
    List<ColumnDTO> columns;
    List<ViewDTO> views;
}
