package com.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Row {

    @Id
    private String id;
    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells;
    @ManyToOne
    @JoinColumn(name = "smartlist_id")
    private SmartList smartList;

    public Row(SmartList smartList) {
        this.id = UUID.randomUUID().toString();
        this.cells = new ArrayList<>();
        this.smartList = smartList;
    }

    public Row() {
        this.id = UUID.randomUUID().toString();
        cells = new ArrayList<>();
    }

}
