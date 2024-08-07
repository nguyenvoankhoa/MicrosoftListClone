package com;

import com.cloudinary.Cloudinary;
import com.config.CustomMap;
import com.dto.*;
import com.model.*;
import com.repository.CellRepository;
import com.repository.ColumnRepository;
import com.repository.RowRepository;
import com.repository.SmartListRepository;
import com.service.ITemplateService;
import com.service.SmartListService;
import com.type.ColumnType;
import com.type.ConfigType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class SmartListServiceTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SmartListRepository smartListRepository;

    @Mock
    private ITemplateService templateService;

    @Mock
    private RowRepository rowRepository;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private CellRepository cellRepository;

    @Mock
    private CustomMap customMap;

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private SmartListService smartListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateList() {
        String listName = "Test List";
        SmartList smartList = new SmartList(listName);
        SmartListDTO smartListDTO = new SmartListDTO();

        Mockito.when(smartListRepository.findSmartListByName(listName)).thenReturn(null);
        when(smartListRepository.save(any(SmartList.class))).thenReturn(smartList);
        when(modelMapper.map(smartList, SmartListDTO.class)).thenReturn(smartListDTO);

        SmartListDTO result = smartListService.createList(listName);

        Assertions.assertNotNull(result);
        verify(smartListRepository).findSmartListByName(listName);
        verify(smartListRepository).save(any(SmartList.class));
        verify(modelMapper).map(smartList, SmartListDTO.class);
    }


    @Test
    void testGetAllRows() {
        String smartListId = "123";
        Row row = new Row();
        List<Row> rows = List.of(row);
        RowDTO rowDTO = new RowDTO();
        when(rowRepository.findRowsBySmartListId(smartListId)).thenReturn(rows);
        when(modelMapper.map(row, RowDTO.class)).thenReturn(rowDTO);

        List<RowDTO> result = smartListService.getAllRows(smartListId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(rowDTO, result.get(0));
        verify(rowRepository).findRowsBySmartListId(smartListId);
    }

    @Test
    void testCreateListFromTemplate() {
        String templateId = "template-id";
        String listName = "New List from Template";
        Template template = new Template();
        SmartList smartList = new SmartList(listName);
        SmartListDTO smartListDTO = new SmartListDTO();

        when(templateService.getTemplateById(templateId)).thenReturn(template);
        when(smartListRepository.save(any(SmartList.class))).thenReturn(smartList);
        when(modelMapper.map(smartList, SmartListDTO.class)).thenReturn(smartListDTO);

        SmartListDTO result = smartListService.createListFromTemplate(new TemplateToListDTO(templateId, listName));

        Assertions.assertNotNull(result);
        verify(templateService).getTemplateById(templateId);
        verify(smartListRepository).save(any(SmartList.class));
        verify(modelMapper).map(smartList, SmartListDTO.class);
    }


    @Test
    void testGetRowById() {
        String rowId = "123";
        Row row = new Row();
        RowDTO rowDTO = new RowDTO();
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));
        when(modelMapper.map(row, RowDTO.class)).thenReturn(rowDTO);
        RowDTO result = smartListService.getRowById(rowId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(rowDTO, result);
        verify(rowRepository).findById(rowId);
        verify(modelMapper).map(row, RowDTO.class);
    }

    @Test
    void testDeleteRow() {
        String rowId = "row123";
        String listId = "list456";
        Row row = new Row();
        SmartList smartList = new SmartList();
        smartList.setRows(new ArrayList<>(List.of(row)));
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));
        when(smartListRepository.findById(listId)).thenReturn(Optional.of(smartList));
        smartListService.deleteRow(rowId, listId);
        Assertions.assertTrue(smartList.getRows().isEmpty(), "Row should be removed from SmartList");
        verify(rowRepository).deleteById(rowId);
        verify(smartListRepository).save(smartList);
    }

    @Test
    void testAddCellData() {
        CreateCellDTO createCellDTO = new CreateCellDTO();
        Cell cell = new Cell();
        CellDTO cellDTO = new CellDTO();
        when(smartListService.createCell(createCellDTO)).thenReturn(cell);
        when(modelMapper.map(cell, CellDTO.class)).thenReturn(cellDTO);
        CellDTO result = smartListService.addCellData(createCellDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(cellDTO, result);
        verify(modelMapper).map(cell, CellDTO.class);
    }

    @Test
    void testCreateColumn() {
        String smartListId = "smartList123";
        CreateColumnDTO createReq = new CreateColumnDTO();
        createReq.setName("New Column");
        createReq.setAllowDefault(true);
        createReq.setColumnType(ColumnType.TEXT);
        List<ColumnConfigDTO> configDTOs = List.of(
                new ColumnConfigDTO("value1", ConfigType.DEFAULT_VALUE)
        );
        createReq.setConfigs(configDTOs);


        SmartList smartList = new SmartList();
        smartList.setColumns(new ArrayList<>());
        smartList.setRows(new ArrayList<>());
        Column newColumn = new Column("New Column", true, ColumnType.TEXT, smartList);
        List<ColumnConfig> configs = List.of(
                new ColumnConfig("value1", ConfigType.DEFAULT_VALUE)
        );
        newColumn.setConfigs(configs);

        when(modelMapper.map(any(Column.class), eq(ColumnDTO.class))).thenReturn(new ColumnDTO());

        when(smartListRepository.findById(smartListId)).thenReturn(Optional.of(smartList));
        when(smartListRepository.save(smartList)).thenReturn(smartList);

        ColumnDTO result = smartListService.createColumn(smartListId, createReq);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(new ColumnDTO(), result);

        verify(smartListRepository).findById(smartListId);
        verify(smartListRepository).save(smartList);
        verify(modelMapper).map(newColumn, ColumnDTO.class);
    }
}
