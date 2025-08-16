package de.deleteboys.domain.log;

import de.deleteboys.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "action_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Log extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;
}