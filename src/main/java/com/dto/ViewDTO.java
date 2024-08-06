package com.dto;


import com.type.ViewType;
import lombok.Data;

import java.util.List;

@Data
public class ViewDTO {
    List<RowDTO> rows;
    ViewType viewType;
    String name;
}
