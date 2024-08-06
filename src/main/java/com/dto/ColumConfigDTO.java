package com.dto;

import com.type.ConfigType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumConfigDTO {
    String id;
    String configName;
    String configValue;
    ConfigType configType;
}
