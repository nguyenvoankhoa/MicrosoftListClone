package com.service;

import com.dto.ListToTemplateDTO;
import com.dto.TemplateDTO;
import com.model.SmartList;
import com.model.Template;
import com.repository.SmartListRepository;
import com.repository.TemplateRepository;
import com.util.Common;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService implements ITemplateService {
    private TemplateRepository templateRepository;
    private SmartListRepository smartListRepository;
    private ModelMapper modelMapper;

    public TemplateService(TemplateRepository templateRepository, SmartListRepository smartListRepository, ModelMapper modelMapper) {
        this.templateRepository = templateRepository;
        this.smartListRepository = smartListRepository;
        this.modelMapper = modelMapper;
    }

    public TemplateDTO saveListToTemplate(ListToTemplateDTO request) {
        SmartList sl = smartListRepository.findSmartListById(request.getListId());
        Common.checkExist(templateRepository.findTemplateByName(request.getTemplateName()));
        Template template = new Template(request.getTemplateName(), sl);
        return modelMapper.map(templateRepository.save(template), TemplateDTO.class);
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        List<Template> templates = templateRepository.findAll();
        return templates.stream().map(t -> modelMapper.map(t, TemplateDTO.class)).toList();
    }

    public Template getTemplateById(String id) {
        Template t = templateRepository.findById(id).orElse(null);
        Common.checkNonExist(t);
        return t;
    }


}
