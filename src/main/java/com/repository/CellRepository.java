package com.repository;

import com.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, String> {
    void deleteCellByColumn_Id(String colId);

    long countAllByColumn_Id(String colId);

    @Query("SELECT DISTINCT c.data FROM Cell c WHERE c.column.id = :colId")
    List<String> findDistinctDataByColumnId(@Param("colId") String colId);

    @Query("SELECT c from Cell c WHERE c.row.id = :rowId")
    List<Cell> findCellsByRowId(@Param("rowId") String rowId);

    @Query("SELECT c from Cell c WHERE c.row.id = :rowId and c.column.id = :colId")
    Cell findCellByRowAndColumn(@Param("rowId") String rowId, @Param("colId") String colId);
}
