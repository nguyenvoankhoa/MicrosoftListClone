package com.dto;

import com.type.ConfigType;
import lombok.Data;

@Data
public class ColumnConfigDTO {
    String configValue;
    ConfigType configType;
}
