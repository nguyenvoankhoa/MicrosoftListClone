package com.dto;

import com.type.ConfigType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnConfigDTO {
    String configValue;
    ConfigType configType;
}
