package com.dto;

import com.type.ColumnType;
import lombok.Data;

import java.util.List;

@Data
public class CreateColumnDTO {
    String name;
    boolean allowDefault;
    ColumnType columnType;
    List<ColumnConfigDTO> configs;
}
