package de.deleteboys.domain.log;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_log")
@DiscriminatorValue("LOGIN")
public class LoginLog extends Log {

    @Column(name = "ip_address")
    public String ipAddress;

    @Column(name = "user_agent")
    public String userAgent;

    @Column
    public boolean success;
}