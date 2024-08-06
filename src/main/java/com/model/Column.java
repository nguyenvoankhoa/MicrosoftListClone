package com.model;

import com.type.ColumnType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "column_entity")
public class Column {
    @Id
    private String id;
    private String name;
    private boolean isVisible;
    private boolean allowDefault;
    @Enumerated(EnumType.STRING)
    private ColumnType type;
    @ManyToOne
    @JoinColumn(name = "smartlist_id")
    private SmartList smartList;

    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColumnConfig> configs;

    public Column(String name, boolean allowDefault, ColumnType type, SmartList smartList) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.isVisible = true;
        this.allowDefault = allowDefault;
        this.type = type;
        this.configs = new ArrayList<>();
        this.smartList = smartList;
    }
}

