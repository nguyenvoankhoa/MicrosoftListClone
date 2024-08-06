package com.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Template {
    @Id
    private String id;

    private String name;
    @OneToOne
    private SmartList smartList;

    public Template(String name, SmartList smartList) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.smartList = smartList;
    }
}
