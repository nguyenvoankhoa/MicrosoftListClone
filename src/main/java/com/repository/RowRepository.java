package com.repository;

import com.model.Row;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RowRepository extends JpaRepository<Row, String> {
    @Query("SELECT r FROM Row r JOIN r.cells c WHERE c.data = :data AND c.column.id = :colId")
    List<Row> findRowsByCellDataAndColumnId(@Param("data") String data, @Param("colId") String columnId);

    @Query("SELECT r FROM Row r LEFT JOIN FETCH r.cells WHERE r.smartList.id = :listId")
    List<Row> findRowsBySmartListId(@Param("listId") String listId);

    List<Row> findAllBySmartList_Id(String listId, Pageable pageable);

    @Query("SELECT r FROM Row r JOIN r.cells c WHERE r.smartList.id = :listId AND c.column.id = :columnId ORDER BY c.data ASC")
    List<Row> findRowsSortedByCellValue(@Param("listId") String listId, @Param("columnId") String columnId, Pageable pageable);

    @Query("SELECT r FROM Row r JOIN r.cells c WHERE r.smartList.id = :listId AND c.column.id = :columnId ORDER BY c.data DESC")
    List<Row> findRowsSortedByCellValueDesc(@Param("listId") String listId, @Param("columnId") String columnId, Pageable pageable);

    @Query("SELECT r FROM Row r JOIN r.cells c WHERE c.id = :columnId")
    List<Row> findRowsByColumnId(@Param("columnId") String columnId);
}
