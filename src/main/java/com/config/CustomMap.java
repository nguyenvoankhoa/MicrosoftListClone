package com.config;

import com.dto.*;
import com.factory.DataFactory;
import com.model.*;
import org.modelmapper.ModelMapper;

import java.util.List;

public class CustomMap {

    private ModelMapper modelMapper = new ModelMapper();

    public ViewDTO mapView(View v) {
        ViewDTO dto = new ViewDTO();
        dto.setName(v.getName());
        dto.setViewType(v.getViewType());
        dto.setRows(mapRowDTO(v.getSmartList().getRows()));
        return dto;
    }

    public CellDTO mapCell(Cell cell) {
        DataFactory df = new DataFactory();
        CellDTO dto = new CellDTO();
        dto.setColId(cell.getId());
        dto.setRowId(cell.getRowId());
        dto.setColId(cell.getColumnId());
        dto.setData(df.createData(cell.getData(), cell.getColumnType()));
        return dto;
    }

    public ColumnDTO mapColumn(Column column) {
        ColumnDTO dto = modelMapper.map(column, ColumnDTO.class);
        List<ColumnConfigDTO> configDTOs = column.getConfigs().stream()
                .map(config -> modelMapper.map(config, ColumnConfigDTO.class))
                .toList();
        dto.setConfigs(configDTOs);
        return dto;
    }

    public RowDTO mapRow(Row row) {
        RowDTO dto = new RowDTO();
        List<CellDTO> cellDTOs = row.getCells().stream()
                .map(this::mapCell)
                .toList();
        dto.setRowId(row.getId());
        dto.setRowData(cellDTOs);
        return dto;
    }

    public List<RowDTO> mapRowDTO(List<Row> rows) {
        return rows.stream()
                .map(row -> {
                    List<CellDTO> cellDTOs = row.getCells().stream()
                            .map(this::mapCell)
                            .toList();
                    return new RowDTO(row.getId(), cellDTOs);
                }).toList();
    }

    public List<ColumnDTO> mapColumnDTO(List<Column> columns) {
        return columns.stream().map(this::mapColumn).toList();
    }

    public List<ViewDTO> mapViewDTO(List<View> views) {
        return views.stream().map(this::mapView).toList();
    }

    public SmartListDTO mapSmartListDTO(SmartList smartList) {
        SmartListDTO dto = new SmartListDTO();
        dto.setRows(mapRowDTO(smartList.getRows()));
        dto.setId(smartList.getId());
        dto.setName(smartList.getName());
        dto.setColumns(mapColumnDTO(smartList.getColumns()));
        dto.setViews(mapViewDTO(smartList.getViews()));
        return dto;
    }
}
