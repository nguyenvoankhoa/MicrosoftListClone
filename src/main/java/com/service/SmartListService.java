package com.service;

import com.config.CustomMap;
import com.dto.*;
import com.exception.ConstraintViolationException;
import com.model.*;
import com.repository.*;
import com.type.ConfigType;
import com.util.Common;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class SmartListService implements ISmartListService {
    private ModelMapper modelMapper;
    private SmartListRepository smartListRepository;
    private ITemplateService templateService;
    private RowRepository rowRepository;
    private ColumnRepository columnRepository;
    private CellRepository cellRepository;
    private CustomMap customMap;

    public SmartListService(ModelMapper modelMapper, SmartListRepository smartListRepository, ITemplateService templateService, RowRepository rowRepository, ColumnRepository columnRepository, CellRepository cellRepository) {
        this.modelMapper = modelMapper;
        this.smartListRepository = smartListRepository;
        this.templateService = templateService;
        this.rowRepository = rowRepository;
        this.columnRepository = columnRepository;
        this.cellRepository = cellRepository;
        this.customMap = new CustomMap();
    }

    public SmartListDTO createList(String name) {
        Common.checkExist(smartListRepository.findSmartListByName(name));
        SmartList sl = new SmartList(name);
        return modelMapper.map(smartListRepository.save(sl), SmartListDTO.class);
    }


    @Override
    public List<RowDTO> getAllRows(String id) {
        List<Row> rows = rowRepository.findRowsBySmartListId(id);
        return rows.stream().map(r -> modelMapper.map(r, RowDTO.class)).toList();
    }


    @Override
    public SmartListDTO getSortedAndPagedSmartList(String listId, String sortBy, String order, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        boolean ascending = Optional.ofNullable(order).map("asc"::equalsIgnoreCase).orElse(true);
        List<Row> rows = Optional.ofNullable(sortBy)
                .map(s -> ascending ?
                        rowRepository.findRowsSortedByCellValue(listId, s, pageable) :
                        rowRepository.findRowsSortedByCellValueDesc(listId, s, pageable))
                .orElseGet(() -> rowRepository.findAllBySmartList_Id(listId, pageable));

        SmartList sl = getSmartListById(listId);
        sl.setRows(rows);

        return modelMapper.map(sl, SmartListDTO.class);
    }


    @Override
    public SmartListDTO addRowData(RowDataDTO request) {
        List<CreateCellDTO> cells = request.getRow();
        for (CreateCellDTO c : cells) {
            createCell(c);
        }
        return null;
    }

    @Override
    public RowDTO getRowById(String rowId) {
        return modelMapper.map(getRow(rowId), RowDTO.class);
    }

    @Override
    public void deleteRow(String rowId, String listId) {
        Row row = getRow(rowId);
        SmartList sl = getSmartListById(listId);
        sl.getRows().remove(row);
        rowRepository.deleteById(rowId);
        smartListRepository.save(sl);
    }

    @Override
    public CreateCellDTO addCellData(CreateCellDTO request) {
        return modelMapper.map(createCell(request), CreateCellDTO.class);
    }

    public Cell createCell(CreateCellDTO request) {
        Column column = getColumn(request.getColId());
        Row row = getRow(request.getRowId());
        checkCellConstraint(column.getConfigs(), request.getData());
        Cell c = cellRepository.findCellByRowAndColumn(row.getId(), column.getId());
        c.setData(request.getData());
        return cellRepository.save(c);
    }


    public Row getRow(String rowId) {
        Row row = rowRepository.findById(rowId).orElse(null);
        Common.checkNonExist(row);
        return row;
    }

    public Column getColumn(String colId) {
        Column column = columnRepository.findById(colId).orElse(null);
        Common.checkNonExist(column);
        return column;
    }

    @Override
    public ColumnDTO getColumnById(String listId, String colId) {
        Column column = columnRepository.getColumnById(listId, colId);
        ColumnDTO columnDTO = modelMapper.map(column, ColumnDTO.class);
        List<ColumnConfigDTO> configDTOs = column.getConfigs().stream()
                .map(config -> modelMapper.map(config, ColumnConfigDTO.class))
                .toList();
        columnDTO.setConfigs(configDTOs);
        return columnDTO;
    }

    @Override
    public ColumnDTO createColumn(String smartListId, CreateColumnDTO createReq) {
        SmartList smartList = getSmartListById(smartListId);
        Column newColumn = new Column(createReq.getName(), createReq.isAllowDefault(), createReq.getColumnType(), smartList);
        List<ColumnConfig> configs = createReq.getConfigs().stream()
                .map(config -> new ColumnConfig(config.getConfigValue(), config.getConfigType(), newColumn))
                .toList();
        newColumn.setConfigs(configs);
        smartList.getColumns().add(newColumn);
        for (Row r : smartList.getRows()) {
            r.getCells().add(new Cell("", r, newColumn));
        }
        smartListRepository.save(smartList);
        return modelMapper.map(newColumn, ColumnDTO.class);
    }

    @Override
    public List<String> getFilters(String colId) {
        return cellRepository.findDistinctDataByColumnId(colId);
    }

    @Override
    public List<RowDTO> filterByColumn(String colId, String filter) {
        List<Row> rows = rowRepository.findRowsByCellDataAndColumnId(filter, colId);
        return rows.stream().map(r -> modelMapper.map(r, RowDTO.class)).toList();
    }

    @Override
    public Map<Object, List<RowDTO>> groupByColumn(String id) {
        List<Row> rows = rowRepository.findRowsByColumnId(id);
        List<RowDTO> dtoList = customMap.mapRowDTO(rows);

        return dtoList.stream()
                .collect(Collectors.groupingBy(row -> findCellByColumnId(row.getRowData(), id)));
    }

    public Object findCellByColumnId(List<CellDTO> cells, String colId) {
        return cells.stream()
                .filter(c -> c.getColId().equals(colId))
                .findFirst()
                .map(CellDTO::getData)
                .orElse(null);
    }


    @Override
    public long countByColumn(String id) {
        return cellRepository.countAllByColumn_Id(id);
    }


    @Override
    public void removeColumn(String id) {
        cellRepository.deleteCellByColumn_Id(id);
        columnRepository.deleteById(id);
    }


    @Override
    public void createRow(String id) {
        SmartList smartList = getSmartListById(id);
        List<Column> columns = smartList.getColumns();
        Row newRow = new Row(smartList);
        List<Cell> newCells = columns.stream()
                .map(column -> new Cell("", newRow, column))
                .toList();
        newRow.setCells(newCells);
        rowRepository.save(newRow);
        smartList.getRows().add(newRow);
        smartListRepository.save(smartList);
    }

    public SmartListDTO createListFromTemplate(TemplateToListDTO request) {
        Template t = templateService.getTemplateById(request.getTemplateId());
        SmartList sl = new SmartList(request.getListName(), t.getSmartList().getColumns());
        return modelMapper.map(smartListRepository.save(sl), SmartListDTO.class);
    }

    public SmartList getSmartListById(String id) {
        SmartList sl = smartListRepository.findById(id).orElse(null);
        Common.checkNonExist(sl);
        return sl;
    }

    public List<SmartListDTO> getAllLists() {
        List<SmartList> lists = smartListRepository.findAll();
        return lists.stream().map(l -> modelMapper.map(l, SmartListDTO.class)).toList();
    }

    public void checkCellConstraint(List<ColumnConfig> configs, String data) {
        for (ColumnConfig config : configs) {
            ConfigType configType = config.getConfigType();
            String configValue = config.getConfigValue();
            switch (configType) {
                case MAX_CHARACTER:
                    Common.validateMaxCharacter(data, configValue);
                    break;
                case REQUIRE:
                    Common.validateRequirement(data);
                    break;
                case MIN_VALUE:
                    Common.validateMinValue(data, configValue);
                    break;
                case MAX_VALUE:
                    Common.validateMaxValue(data, configValue);
                    break;
                case DEFAULT_VALUE:
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ConfigType: " + configType);
            }
        }
    }


}
