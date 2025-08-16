package de.deleteboys.domain.log;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "update_log")
@DiscriminatorValue("UPDATE")
public class UpdateLog extends Log {

    @Column(name = "table_name")
    public String tableName;

    @Column(name = "field_name")
    public String fieldName;

    @Column(name = "old_value", columnDefinition = "TEXT")
    public String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    public String newValue;
}