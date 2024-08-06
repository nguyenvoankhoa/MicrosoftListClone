package com.dto;

import com.model.SmartList;
import com.type.ViewType;
import lombok.Data;

@Data
public class CreateViewDTO {
    SmartList smartList;
    ViewType viewType;
    String name;
}
