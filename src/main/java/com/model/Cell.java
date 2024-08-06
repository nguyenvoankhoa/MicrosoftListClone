package com.model;

import com.type.ColumnType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Cell {
    @Id
    private String id;
    private String data;
    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Column column;

    public Cell(String data, Row row, Column column) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.row = row;
        this.column = column;
    }

    public String getColumnId() {
        return column.getId();
    }
    public String getRowId() {
        return row.getId();
    }
    public ColumnType getColumnType() {
        return column.getType();
    }
}
