package com.model;

import com.type.ColumnType;
import com.type.ConfigType;
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
public class ColumnConfig {
    @Id
    private String id;
    private String configValue;
    private ConfigType configType;
    @ManyToOne
    @JoinColumn(name = "column_id")
    Column column;

    public ColumnConfig(String configValue, ConfigType configType, Column column) {
        this.id = UUID.randomUUID().toString();
        this.configValue = configValue;
        this.configType = configType;
        this.column = column;
    }
}
