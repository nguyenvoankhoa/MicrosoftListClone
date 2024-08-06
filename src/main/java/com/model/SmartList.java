package com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SmartList {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "smartList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Row> rows;

    @OneToMany(mappedBy = "smartList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Column> columns;

    @OneToMany(mappedBy = "smartList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> views;

    public SmartList(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rows = new ArrayList<>();
        this.columns = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public SmartList(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
        this.views = new ArrayList<>();
    }
}
