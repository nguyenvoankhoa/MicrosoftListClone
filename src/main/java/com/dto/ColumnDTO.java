package com.dto;

import com.type.ColumnType;
import lombok.Data;

import java.util.List;

@Data
public class ColumnDTO {
    String id;
    String name;
    boolean isVisible;
    boolean allowDefault;
    ColumnType type;
    List<ColumnConfigDTO> configs;
}
