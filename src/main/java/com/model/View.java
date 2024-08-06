package com.model;

import com.type.ViewType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "view_entity")
public class View {
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "smartlist_id")
    private SmartList smartList;
    private ViewType viewType;

    public View(SmartList smartList, ViewType viewType, String name) {
        this.id = UUID.randomUUID().toString();
        this.smartList = smartList;
        this.viewType = viewType;
        this.name = name;
    }
}
