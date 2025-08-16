package de.deleteboys.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;

    @Column
    public String description;
}