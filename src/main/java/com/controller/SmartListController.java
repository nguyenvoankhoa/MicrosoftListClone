package com.controller;

import com.dto.*;
import com.service.ISmartListService;
import com.service.IViewService;
import com.type.ColumnType;
import com.type.ConfigType;
import com.type.ViewType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/smart-lists")
public class SmartListController {

    private ISmartListService smartListService;

    private IViewService viewService;

    public SmartListController(ISmartListService smartListService, IViewService viewService) {
        this.smartListService = smartListService;
        this.viewService = viewService;
    }

    @PostMapping("data")
    public ResponseEntity<CellDTO> addCellData(@RequestBody CreateCellDTO request) {
        CellDTO dto = smartListService.addCellData(request);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SmartListDTO> getSmartList(@RequestParam(name = "sortBy", required = false) String sortBy,
                                                     @RequestParam(name = "order", required = false) String order,
                                                     @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                     @PathVariable String id) {
        SmartListDTO dto = smartListService.getSortedAndPagedSmartList(id, sortBy, order, pageNum, pageSize);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("rows")
    public ResponseEntity<List<CellDTO>> addRowData(@RequestBody RowDataDTO request) {
        List<CellDTO> dto = smartListService.addRowData(request);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("rows/{id}")
    public ResponseEntity<RowDTO> getRowData(@PathVariable("id") String rowId) {
        RowDTO dto = smartListService.getRowById(rowId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("rows/{id}")
    public ResponseEntity deleteRowData(@PathVariable("id") String rowId,
                                        @RequestParam("listId") String listId) {
        smartListService.deleteRow(rowId, listId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @GetMapping("column-types")
    public ResponseEntity<ColumnType[]> getColumnTypes() {
        ColumnType[] columnTypes = ColumnType.values();
        return new ResponseEntity<>(columnTypes, HttpStatus.OK);
    }

    @GetMapping("{id}/columns")
    public ResponseEntity<ColumnDTO> getColumnById(@RequestParam(name = "columnId") String colId, @PathVariable String id) {
        ColumnDTO column = smartListService.getColumnById(id, colId);
        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @PostMapping("{id}/columns")
    public ResponseEntity<ColumnDTO> createColumn(@RequestBody CreateColumnDTO createReq, @PathVariable String id) {
        ColumnDTO column = smartListService.createColumn(id, createReq);
        return new ResponseEntity<>(column, HttpStatus.CREATED);
    }


    @GetMapping("columns/filters/{id}")
    public ResponseEntity<List<String>> getColumnFilters(@PathVariable String id) {
        List<String> filters = smartListService.getFilters(id);
        return new ResponseEntity<>(filters, HttpStatus.OK);
    }


    @PostMapping("columns/filters/{id}")
    public ResponseEntity<List<RowDTO>> filterColumn(@PathVariable String id, @RequestBody String filter) {
        List<RowDTO> rows = smartListService.filterByColumn(id, filter);
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

    @PostMapping("columns/group/{id}")
    public ResponseEntity<Map<Object, List<RowDTO>>> groupByColumn(@PathVariable String id) {
        Map<Object, List<RowDTO>> groupedRows = smartListService.groupByColumn(id);
        return new ResponseEntity<>(groupedRows, HttpStatus.OK);
    }

    @PostMapping("columns/count/{id}")
    public ResponseEntity<Long> countColumn(@PathVariable String id) {
        long colNum = smartListService.countByColumn(id);
        return new ResponseEntity<>(colNum, HttpStatus.OK);
    }

    @DeleteMapping("columns/{id}")
    public ResponseEntity removeColumn(@PathVariable String id) {
        smartListService.removeColumn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("view-types")
    public ResponseEntity<ViewType[]> getViewTypes() {
        ViewType[] viewTypes = ViewType.values();
        return new ResponseEntity<>(viewTypes, HttpStatus.OK);
    }

    @GetMapping("constraint-types")
    public ResponseEntity<ConfigType[]> getConfigTypes() {
        ConfigType[] configTypes = ConfigType.values();
        return new ResponseEntity<>(configTypes, HttpStatus.OK);
    }

    @GetMapping("{id}/rows")
    public ResponseEntity<List<RowDTO>> getAllRows(@PathVariable String id) {
        List<RowDTO> dto = smartListService.getAllRows(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("{id}/rows")
    public ResponseEntity createNewRow(@PathVariable String id) {
        smartListService.createRow(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("{id}/views")
    public ResponseEntity<ViewDTO> createView(@RequestBody CreateViewDTO request, @PathVariable String id) {
        ViewDTO dto = viewService.createView(id, request);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
