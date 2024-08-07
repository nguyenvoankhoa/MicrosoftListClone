package com;

import com.controller.MicrosoftListController;
import com.dto.*;
import com.dto.datatype.Text;
import com.service.ISmartListService;
import com.service.ITemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
        requestDTO.setTemplateId("1");
        requestDTO.setListName("List from template");


        SmartListDTO responseDTO = new SmartListDTO();
        responseDTO.setId("1");
        responseDTO.setName("List from Template");
        responseDTO.setRows(new ArrayList<>());
        responseDTO.setColumns(new ArrayList<>());
        responseDTO.setViews(new ArrayList<>());


        when(smartListService.createListFromTemplate(any(TemplateToListDTO.class))).thenReturn(responseDTO);
        String requestJson = "{\"templateId\": \"1\", \"listName\": \"List from template\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/microsoft-lists/lists/from-template")
                        .content(requestJson)
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

        mockMvc.perform(MockMvcRequestBuilders.post("/microsoft-lists/lists/save-template")
                        .content("{\"templateName\": \"Template from list\", \"listId\": \"c4c91d94-8530-4b48-9880-c03eeeae1909\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.templateName").value(responseDTO.getTemplateName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.columns").isArray());
    }

    @Test
    void testAddRowData() throws Exception {
        CreateCellDTO cell1 = new CreateCellDTO();
        cell1.setColId("col1");
        cell1.setRowId("row1");
        cell1.setData("Data1");

        CreateCellDTO cell2 = new CreateCellDTO();
        cell2.setColId("col2");
        cell2.setRowId("row1");
        cell2.setData("Data2");

        RowDataDTO requestDTO = new RowDataDTO();
        requestDTO.setRow(List.of(cell1, cell2));

        CellDTO cell11 = new CellDTO();
        cell11.setColId("col1");
        cell11.setRowId("row1");
        Text text = new Text("Sample Data");
        cell11.setData(text);

        when(smartListService.addRowData(any(RowDataDTO.class))).thenReturn(List.of(cell11));

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/rows")
                        .content("{\"row\":[{\"colId\":\"col1\",\"rowId\":\"row1\",\"data\":\"Data1\"},{\"colId\":\"col2\",\"rowId\":\"row1\",\"data\":\"Data2\"}]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
