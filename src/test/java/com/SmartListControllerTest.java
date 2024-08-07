package com;

import com.controller.SmartListController;
import com.dto.*;
import com.service.ISmartListService;
import com.type.ColumnType;
import com.type.ConfigType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmartListControllerTest {

    private MockMvc mockMvc;
    private String listId;
    @Mock
    private ISmartListService smartListService;

    @InjectMocks
    private SmartListController smartListController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(smartListController).build();
        listId = "1b481aa1-929c-451f-8a97-808ff8aeccc0";
    }

    @Test
    void testCreateTextColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Text Column");
        createColumnDTO.setColumnType(ColumnType.TEXT);
        createColumnDTO.setAllowDefault(true);
        ColumnConfigDTO config = new ColumnConfigDTO();
        config.setConfigType(ConfigType.MAX_CHARACTER);
        config.setConfigValue("30");
        createColumnDTO.setConfigs(List.of(config));

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Text Column");
        responseDTO.setType(ColumnType.TEXT);
        responseDTO.setConfigs(List.of(config));
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Text Column\", \"columnType\": \"TEXT\", \"allowDefault\": true, \"configs\": [{\"configType\": \"MAX_CHARACTER\", \"configValue\": \"30\"}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }

    @Test
    void testCreateChoiceColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Choice Column");
        createColumnDTO.setColumnType(ColumnType.CHOICE);
        createColumnDTO.setAllowDefault(true);
        ColumnConfigDTO config = new ColumnConfigDTO();
        config.setConfigType(ConfigType.DEFAULT_VALUE);
        config.setConfigValue("Choice 1");
        createColumnDTO.setConfigs(List.of(config));

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Choice Column");
        responseDTO.setType(ColumnType.CHOICE);
        responseDTO.setConfigs(List.of(config));
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Choice Column\", \"columnType\": \"CHOICE\"," +
                " \"allowDefault\": true, \"configs\": [{\"configType\": \"DEFAULT_VALUE\", \"configValue\": \"Choice 1\"}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }


    @Test
    void testCreateMultipleChoiceColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Multiple choice Column");
        createColumnDTO.setColumnType(ColumnType.MULTIPLE_CHOICE);
        createColumnDTO.setAllowDefault(true);
        ColumnConfigDTO config = new ColumnConfigDTO();
        config.setConfigType(ConfigType.DEFAULT_VALUE);
        config.setConfigValue("Choice 1");
        ColumnConfigDTO config2 = new ColumnConfigDTO();
        config2.setConfigType(ConfigType.DEFAULT_VALUE);
        config2.setConfigValue("Choice 2");
        createColumnDTO.setConfigs(List.of(config, config2));

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Multiple choice Column");
        responseDTO.setType(ColumnType.MULTIPLE_CHOICE);
        responseDTO.setConfigs(List.of(config, config2));
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Multiple choice Column\", \"columnType\": \"MULTIPLE_CHOICE\", " +
                "\"allowDefault\": true, \"configs\": [{\"configType\": \"DEFAULT_VALUE\", \"configValue\": \"Choice 1\"}," +
                " {\"configType\": \"DEFAULT_VALUE\", \"configValue\": \"Choice 2\"}]}";


        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }


    @Test
    void testCreateNumberColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Number Column");
        createColumnDTO.setColumnType(ColumnType.NUMBER);
        createColumnDTO.setAllowDefault(true);
        ColumnConfigDTO config = new ColumnConfigDTO();
        config.setConfigType(ConfigType.MIN_VALUE);
        config.setConfigValue("1");
        ColumnConfigDTO config2 = new ColumnConfigDTO();
        config2.setConfigType(ConfigType.MIN_VALUE);
        config2.setConfigValue("300");
        createColumnDTO.setConfigs(List.of(config, config2));

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Number Column");
        responseDTO.setType(ColumnType.NUMBER);
        responseDTO.setConfigs(List.of(config, config2));
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Number Column\", \"columnType\": \"NUMBER\", " +
                "\"allowDefault\": true, \"configs\": [{\"configType\": \"MIN_VALUE\", \"configValue\": \"1\"}," +
                " {\"configType\": \"MIN_VALUE\", \"configValue\": \"300\"}]}";


        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }


    @Test
    void testCreateImageColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Image Column");
        createColumnDTO.setColumnType(ColumnType.IMAGE);
        createColumnDTO.setAllowDefault(true);
        createColumnDTO.setConfigs(new ArrayList<>());

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Image Column");
        responseDTO.setType(ColumnType.IMAGE);
        responseDTO.setConfigs(new ArrayList<>());
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Image Column\", \"columnType\": \"IMAGE\", " +
                "\"allowDefault\": true, \"configs\": []}";


        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }

    @Test
    void testCreateHyperLinkColumn() throws Exception {
        CreateColumnDTO createColumnDTO = new CreateColumnDTO();
        createColumnDTO.setName("Hyperlink Column");
        createColumnDTO.setColumnType(ColumnType.HYPERLINK);
        createColumnDTO.setAllowDefault(true);
        createColumnDTO.setConfigs(new ArrayList<>());

        ColumnDTO responseDTO = new ColumnDTO();
        responseDTO.setName("Hyperlink Column");
        responseDTO.setType(ColumnType.HYPERLINK);
        responseDTO.setConfigs(new ArrayList<>());
        responseDTO.setVisible(true);
        responseDTO.setAllowDefault(true);

        when(smartListService.createColumn(anyString(), any(CreateColumnDTO.class))).thenReturn(responseDTO);

        String jsonContent = "{\"name\": \"Hyperlink Column\", \"columnType\": \"HYPERLINK\", \"allowDefault\": true, \"configs\": []}";

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/{id}/columns", listId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(responseDTO.getType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.configs").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allowDefault").value(responseDTO.isAllowDefault()));
    }



    @Test
    void testAddCellData() throws Exception {
        CreateCellDTO requestDTO = new CreateCellDTO();
        requestDTO.setColId("col1");
        requestDTO.setRowId("row1");
        requestDTO.setData("Sample Data");

        CellDTO responseDTO = new CellDTO();
        responseDTO.setColId("col1");
        responseDTO.setRowId("row1");
        responseDTO.setData("Sample Data");

        when(smartListService.addCellData(any(CreateCellDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/data")
                        .content("{\"colId\": \"col1\", \"rowId\": \"row1\", \"data\": \"Sample Data\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.colId").value(responseDTO.getColId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rowId").value(responseDTO.getRowId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(responseDTO.getData()));
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

        SmartListDTO responseDTO = new SmartListDTO();
        responseDTO.setName("List Name");
        responseDTO.setRows(new ArrayList<>());
        responseDTO.setColumns(new ArrayList<>());
        responseDTO.setViews(new ArrayList<>());

        when(smartListService.addRowData(any(RowDataDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/rows")
                        .content("{\"row\":[{\"colId\":\"col1\",\"rowId\":\"row1\",\"data\":\"Data1\"},{\"colId\":\"col2\",\"rowId\":\"row1\",\"data\":\"Data2\"}]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.columns").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.views").isArray());
    }


}