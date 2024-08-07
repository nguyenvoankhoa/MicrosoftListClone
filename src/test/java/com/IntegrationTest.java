package com;

import com.dto.*;
import com.dto.datatype.Choice;
import com.dto.datatype.Image;
import com.dto.datatype.MultipleChoice;
import com.dto.datatype.Text;
import com.service.ISmartListService;
import com.type.ColumnType;
import com.type.ConfigType;
import com.util.Common;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IntegrationTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private String listId;
    private String rowId;
    private String colId;
    @Autowired
    private ISmartListService smartListService;


    @BeforeEach
    public void setup() {
        listId = "c4c91d94-8530-4b48-9880-c03eeeae1909";
        colId = "993f45a1-3368-47eb-a004-db856cc27a38";
        rowId = "79fca832-ab4c-4513-bc27-90c185368df2";
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
        requestDTO.setColId(colId);
        requestDTO.setRowId(rowId);
        requestDTO.setData("Sample Data");

        CellDTO responseDTO = new CellDTO();
        responseDTO.setColId(colId);
        responseDTO.setRowId(rowId);
        Text text = new Text("Sample Data");
        responseDTO.setData(text);

        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/data")
                        .content("{\"colId\": \"" + colId + "\", " +
                                "\"rowId\": \"" + rowId + "\", " +
                                "\"data\": \"Sample Data\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.colId").value(responseDTO.getColId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rowId").value(responseDTO.getRowId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.str").value(((Text) responseDTO.getData()).getStr()));
    }

    @Test
    void testAddRowData() throws Exception {
        CreateCellDTO cell1 = new CreateCellDTO();
        cell1.setColId(colId);
        cell1.setRowId(rowId);
        cell1.setData("Sample Data");

        CreateCellDTO cell2 = new CreateCellDTO();
        cell2.setColId(colId);
        cell2.setRowId(rowId);
        cell2.setData("Choice 1,green");

        CreateCellDTO cell3 = new CreateCellDTO();
        cell2.setColId(colId);
        cell2.setRowId(rowId);
        cell2.setData("Choice 1,black#Choice 2,red");

        CreateCellDTO cell4 = new CreateCellDTO();
        cell2.setColId(colId);
        cell2.setRowId(rowId);
        String encode = Common.encodeImageToBase64("src/test/java/com/test-image.jpg");
        cell2.setData(encode);

        RowDataDTO requestDTO = new RowDataDTO();
        requestDTO.setRow(List.of(cell1, cell2, cell3, cell4));


        CellDTO cell11 = new CellDTO();
        cell11.setColId(colId);
        cell11.setRowId(rowId);
        Text text = new Text("Sample Data");
        cell11.setData(text);

        CellDTO cell12 = new CellDTO();
        cell12.setColId(colId);
        cell12.setRowId(rowId);
        Choice choice = new Choice("Choice 1", "green");
        cell12.setData(choice);


        CellDTO cell13 = new CellDTO();
        cell13.setColId(colId);
        cell13.setRowId(rowId);
        Choice c1 = new Choice("Choice 1", "black");
        Choice c2 = new Choice("Choice 2", "red");
        MultipleChoice mulChoice = new MultipleChoice(List.of(c1, c2));
        cell13.setData(mulChoice);


        CellDTO cell14 = new CellDTO();
        cell14.setColId(colId);
        cell14.setRowId(rowId);
        Image image = new Image("");
        cell14.setData(image);


        mockMvc.perform(MockMvcRequestBuilders.post("/smart-lists/rows")
                        .content("{\"row\":[{\"colId\":\"" + colId + "\"," +
                                "\"rowId\":\"" + rowId + "\"," +
                                "\"data\":{\"str\":\"Sample Data\"}}," +
                                "{\"colId\":\"" + colId + "\"," +
                                "\"rowId\":\"" + rowId + "\"," +
                                "\"data\":{\"name\":\"Choice 1\",\"color\":\"green\"}}," +
                                "{\"colId\":\"" + colId + "\"," +
                                "\"rowId\":\"" + rowId + "\"," +
                                "\"data\":{\"choiceList\":[{\"name\":\"Choice 1\",\"color\":\"black\"}," +
                                "{\"name\":\"Choice 2\",\"color\":\"red\"}]}}," +
                                "{\"colId\":\"" + colId + "\"," +
                                "\"rowId\":\"" + rowId + "\"," +
                                "\"data\":{\"url\":\"https://res.cloudinary.com/dsdwh3bxe/image/upload/v1722999075/esvalav6lqbkskz7lwp4.jpg\"}}]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].colId").value(colId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rowId").value(rowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].data.str").value("Sample Data"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].colId").value(colId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].rowId").value(rowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data.name").value("Choice 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data.color").value("green"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].colId").value(colId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].rowId").value(rowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].data.choiceList[0].name").value("Choice 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].data.choiceList[0].color").value("black"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].data.choiceList[1].name").value("Choice 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].data.choiceList[1].color").value("red"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].colId").value(colId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].rowId").value(rowId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].data.url").value("https://res.cloudinary.com/dsdwh3bxe/image/upload/v1722999075/esvalav6lqbkskz7lwp4.jpg"));
    }
}
