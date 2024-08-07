package com.service;

import com.dto.ListToTemplateDTO;
import com.dto.TemplateDTO;
import com.dto.TemplateToListDTO;
import com.model.Template;

import java.util.List;

public interface ITemplateService {

    Template getTemplateById(String id);

    TemplateDTO saveListToTemplate(ListToTemplateDTO request);

    List<TemplateDTO> getAllTemplates();
}
