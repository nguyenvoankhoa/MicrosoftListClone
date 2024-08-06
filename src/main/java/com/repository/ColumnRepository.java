package com.repository;

import com.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColumnRepository extends JpaRepository<Column, String> {

    @Query("SELECT c from Column c LEFT JOIN FETCH c.configs where c.smartList.id = :listId and c.id = :colId")
    Column getColumnById(@Param("listId") String listId, @Param("colId") String colId);
}
