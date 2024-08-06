package com.service;

import com.dto.*;
import com.model.SmartList;

import java.util.List;
import java.util.Map;

public interface ISmartListService {
    SmartList getSmartListById(String id);

    List<SmartListDTO> getAllLists();

    SmartListDTO createListFromTemplate(TemplateToListDTO request);

    SmartListDTO createList(String name);

    SmartListDTO getSortedAndPagedSmartList(String listId, String sortBy, String order, int pageNum, int pageSize);

    SmartListDTO addRowData(RowDataDTO request);

    RowDTO getRowById(String rowId);

    void deleteRow(String rowId, String listId);

    CreateCellDTO addCellData(CreateCellDTO request);

    ColumnDTO getColumnById(String listId, String colId);

    ColumnDTO createColumn(String id, CreateColumnDTO createReq);

    List<String> getFilters(String id);

    List<RowDTO> filterByColumn(String id, String filter);

    Map<Object, List<RowDTO>> groupByColumn(String id);

    long countByColumn(String id);

    void removeColumn(String id);

    List<RowDTO> getAllRows(String id);

    void createRow(String id);
}
