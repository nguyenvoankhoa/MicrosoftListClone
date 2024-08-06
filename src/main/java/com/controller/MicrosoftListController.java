package com.controller;

import com.dto.ListToTemplateDTO;
import com.dto.SmartListDTO;
import com.dto.TemplateDTO;
import com.dto.TemplateToListDTO;
import com.service.ISmartListService;
import com.service.ITemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/microsoft-lists")
public class MicrosoftListController {

    private ISmartListService smartListService;

    private ITemplateService templateService;

    public MicrosoftListController(ISmartListService smartListService, ITemplateService templateService) {
        this.smartListService = smartListService;
        this.templateService = templateService;
    }

    @PostMapping("lists/blank")
    public ResponseEntity<SmartListDTO> createBlankList(@RequestBody String name) {
        SmartListDTO dto = smartListService.createList(name);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("lists/from-template")
    public ResponseEntity<SmartListDTO> createListFromTemplate(@RequestBody TemplateToListDTO request) {
        SmartListDTO dto = smartListService.createListFromTemplate(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("lists/save-template")
    public ResponseEntity<TemplateDTO> saveListAsTemplate(@RequestBody ListToTemplateDTO request) {
        TemplateDTO dto = templateService.saveListToTemplate(request);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("lists")
    public ResponseEntity<List<SmartListDTO>> getAllLists() {
        List<SmartListDTO> dto = smartListService.getAllLists();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("templates")
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        List<TemplateDTO> dto = templateService.getAllTemplates();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
