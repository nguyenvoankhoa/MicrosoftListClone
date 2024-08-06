package com.dto;

import com.model.Column;
import lombok.Data;

import java.util.List;
@Data
public class TemplateDTO {
    String templateName;
    List<Column> columns;
}
