package com;

import com.controller.MicrosoftListController;
import com.dto.ListToTemplateDTO;
import com.dto.SmartListDTO;
import com.dto.TemplateDTO;
import com.dto.TemplateToListDTO;
import com.service.ISmartListService;
import com.service.ITemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MicrosoftListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ISmartListService smartListService;

    @Mock
    private ITemplateService templateService;
    @InjectMocks
    private MicrosoftListController microsoftListController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(microsoftListController).build();
    }

    @Test
    void testCreateBlankList() throws Exception {
        SmartListDTO smartListDTO = new SmartListDTO();
        smartListDTO.setName("New list");
        smartListDTO.setRows(Collections.emptyList());
        smartListDTO.setColumns(Collections.emptyList());
        smartListDTO.setViews(Collections.emptyList());

        when(smartListService.createList(anyString())).thenReturn(smartListDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/microsoft-lists/lists/blank")
                        .content("New List")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(smartListDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.columns").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.views").isArray());
    }

    @Test
    void testCreateListFromTemplate() throws Exception {
        TemplateToListDTO requestDTO = new TemplateToListDTO();
        requestDTO.setTemplateId("");
        requestDTO.setListName("List from template");


        SmartListDTO responseDTO = new SmartListDTO();
        responseDTO.setId("1");
        responseDTO.setName("List from Template");
        responseDTO.setRows(new ArrayList<>());
        responseDTO.setColumns(new ArrayList<>());
        responseDTO.setViews(new ArrayList<>());


        when(smartListService.createListFromTemplate(any(TemplateToListDTO.class))).thenReturn(responseDTO);


        mockMvc.perform(MockMvcRequestBuilders.post("/microsoft-lists/lists/from-template")
                        .content("{\"someField\": \"someValue\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()));
    }

    @Test
    void testSaveListAsTemplate() throws Exception {
        ListToTemplateDTO requestDTO = new ListToTemplateDTO();
        requestDTO.setTemplateName("Template from list");
        requestDTO.setListId("c4c91d94-8530-4b48-9880-c03eeeae1909");


        TemplateDTO responseDTO = new TemplateDTO();
        responseDTO.setTemplateName("Template from list");
        responseDTO.setColumns(new ArrayList<>());

        when(templateService.saveListToTemplate(any(ListToTemplateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/microsoft-lists/lists/from-template")
                        .content("{\"templateName\": \"Template from list\", \"listId\": \"c4c91d94-8530-4b48-9880-c03eeeae1909\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.templateName").value(responseDTO.getTemplateName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.columns").isArray());
    }
}
